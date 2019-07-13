package jjackjjack.sopt.com.jjackjjack.activities.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import jjackjjack.sopt.com.jjackjjack.activities.home.MainActivity
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import jjackjjack.sopt.com.jjackjjack.network.response.post.PostLoginResponse
import jjackjjack.sopt.com.jjackjjack.utillity.ColorToast
import jjackjjack.sopt.com.jjackjjack.utillity.Secret
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy{
        ApplicationController.instance.networkService
    }

    private var mLastClickTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initialUI()

    }

    override fun onResume() {
        super.onResume()
        if(SharedPreferenceController.getAuthorization(this).isNotEmpty()){
            finish()
        }

    }

    private fun LoginResponse(){

        val input_email: String = et_login_email.text.toString()
        val input_pw: String = et_login_pw.text.toString()

        Log.d("login", input_email)
        Log.d("login", input_pw)

        if(!input_email.isNullOrBlank() && input_pw.isNotEmpty()){
            var jsonObject = JSONObject()
            jsonObject.put("email", input_email)
            jsonObject.put("password", input_pw)

            val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

            val postLoginResponse: Call<PostLoginResponse> =
                networkService.postLoginResponse("application/json", gsonObject)

            postLoginResponse.enqueue(object: Callback<PostLoginResponse>{
                override fun onFailure(call: Call<PostLoginResponse>, t: Throwable) {
                    Log.e("Login fail", t.toString())
                    ColorToast(this@LoginActivity, "잠시 후 다시 접속해주세요")
                }

                override fun onResponse(call: Call<PostLoginResponse>, response: Response<PostLoginResponse>) {
                    if(response.isSuccessful){
                        if(response.body()!!.status == Secret.NETWORK_SUCCESS){
                            val token = response.body()!!.data.token
                            val nickname = response.body()!!.data.nickname
                            val email = response.body()!!.data.email
                            val img : String? = response.body()!!.data.profileImg
                            SharedPreferenceController.setAuthorization(this@LoginActivity, token)
                            SharedPreferenceController.setUserNickname(this@LoginActivity, nickname)
                            SharedPreferenceController.setUserEmail(this@LoginActivity, email)
                            if(!img.isNullOrEmpty()) {
                                SharedPreferenceController.setUserImg(this@LoginActivity, nickname)
                            }else{
                                SharedPreferenceController.setUserImg(this@LoginActivity, "")
                            }
                            startActivity<MainActivity>()
                            finish()
                        }
                        else{
                            ColorToast(this@LoginActivity, response.body()!!.message)
                        }
                    }
                }
            })
        }



    }

    private fun initialUI(){
        btn_login_login.setOnClickListener {

            if(SystemClock.elapsedRealtime()-mLastClickTime <1500){
                return@setOnClickListener
            }
            mLastClickTime = SystemClock.elapsedRealtime()

            LoginResponse()
            if(SharedPreferenceController.getAuthorization(this).isNotEmpty()){
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
        }


        btn_login_signup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
            finish()
        }

        btn_login_back.setOnClickListener {
            finish()
        }
    }

}