package jjackjjack.sopt.com.jjackjjack.activities.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import jjackjjack.sopt.com.jjackjjack.activities.MainActivity
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import jjackjjack.sopt.com.jjackjjack.network.response.post.PostLoginResponse
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        et_login_email.setOnFocusChangeListener { v, hasFocus -> //어떤 view(v)가 활성화(has focus)되었는가
//            if(hasFocus){
//                btn_login_login.visibility = View.INVISIBLE
//            }
//            else{
//                btn_login_login.visibility = View.VISIBLE
//            }
//        }
//
//        et_login_pw.setOnFocusChangeListener { v, hasFocus -> //어떤 view(v)가 활성화(has focus)되었는가
//            if(hasFocus){
//                btn_login_login.visibility = View.INVISIBLE
//            }
//            else{
//                btn_login_login.visibility = View.VISIBLE
//            }
//        }

        initialUI()

    }
    private fun LoginResponse(){

        val input_email: String = et_login_email.text.toString()
        val input_pw: String = et_login_pw.text.toString()

        Log.d("login", input_email)
        Log.d("login", input_pw)

        if(input_email.isNotEmpty() && input_pw.isNotEmpty()){
            var jsonObject = JSONObject()
            jsonObject.put("email", input_email)
            jsonObject.put("password", input_pw)

            val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

            val postLoginResponse: Call<PostLoginResponse> =
                networkService.postLoginResponse("application/json", gsonObject)

            postLoginResponse.enqueue(object: Callback<PostLoginResponse>{
                override fun onFailure(call: Call<PostLoginResponse>, t: Throwable) {
                    Log.e("Login fail", t.toString())
                    toast("로그인 실패")
                }

                override fun onResponse(call: Call<PostLoginResponse>, response: Response<PostLoginResponse>) {
                    if(response.isSuccessful){
                        if(response.body()!!.status == Secret.NETWORK_SUCCESS){
                            val token = response.body()!!.data.token
                            SharedPreferenceController.setAuthorization(this@LoginActivity, token)
                            startActivity<MainActivity>()
                            finish()
                        }
                        else{
                            toast(response.body()!!.message)
                        }
                    }
                }
            })
        }



    }

    private fun initialUI(){
        btn_login_login.setOnClickListener {

            LoginResponse()

            if(SharedPreferenceController.getAuthorization(this).isNotEmpty()){

                startActivity<MainActivity>()
                finish()
            }
        }


        btn_login_signup.setOnClickListener {
            startActivity<SignUpActivity>()
        }

        btn_login_back.setOnClickListener {
            finish()
        }
    }

}
