package jjackjjack.sopt.com.jjackjjack.activities.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import jjackjjack.sopt.com.jjackjjack.network.response.post.PostSignUpResponse
import jjackjjack.sopt.com.jjackjjack.utillity.Secret
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private var viewnumber = 1 //회원가입 1, 회원가입 2, 회원가입 3 나중에 상수로 빼서 when
    //전역변수 처리해서 input에 들어간거 넣어줘야함

    lateinit var send_email : String
    lateinit var send_pw : String
    lateinit var send_nickname : String

    private var duplicateCheck = false //중복확인체크 하고 넘어갔는지 아닌지

    val networkService: NetworkService by lazy{
        ApplicationController.instance.networkService
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btn_back.setOnClickListener {
            onBackPressed()
        }

    }

    private fun SignUpResponseData(){
        var jsonObject = JSONObject()
        jsonObject.put("email", send_email)
        jsonObject.put("password", send_pw)
        jsonObject.put("nickname", send_nickname)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        val postSignUpResponse: Call<PostSignUpResponse> =
            networkService.postSignupResponse("application/json", gsonObject)

        postSignUpResponse.enqueue(object: Callback<PostSignUpResponse>{
            override fun onFailure(call: Call<PostSignUpResponse>, t: Throwable) {
                Log.e("Sign fail", t.toString())
                toast("회원가입 실패")
            }

            override fun onResponse(call: Call<PostSignUpResponse>, response: Response<PostSignUpResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == Secret.NETWORK_SUCCESS){
                        startActivity<LoginActivity>()
                        finish()
                    }
                    else{
                        toast(response.body()!!.message)
                    }
                }
            }
        })
    }

    private fun NicknameResponseData(){

    }

    override fun onResume() {
        super.onResume()
        //이거 if-else가 길어질 각이 왔다 onResume으로 옮길수도
        btn_signup_next.setOnClickListener {


            if(viewnumber==1){ //아이디랑 비번 있는지 확인
                val input_email:String = et_signup_section1.text.toString()

                if(input_email.isNotEmpty()){
                    send_email = input_email
                    viewSecondUI()
                }
            }
            else if(viewnumber==2){
                val input_pw:String = et_signup_section1.text.toString()
                val input_pw_verification:String = et_signup_section2.text.toString()
                if(input_pw.isNotEmpty()&&input_pw_verification.isNotEmpty()&&input_pw.contentEquals(input_pw_verification)){
                    send_pw = input_pw
                    viewThirdUI()
                }else if(!input_pw.contentEquals(input_pw_verification)){
                    toast("비밀번호가 일치하지 않습니다.")
                }
            }
            else{

                val input_nickname :String = et_signup_section1.text.toString()

                if(input_nickname.isNotEmpty()){
                    //닉네임 중복 체크 reponse
                    send_nickname = input_nickname
                    //로그인 response

                }
            }
        }
    }

    override fun onBackPressed() {
            //뒤로 가기 눌렀을때 번호 바뀌어야함
        if(viewnumber==1){
            super.onBackPressed()
        }else if(viewnumber == 2){
           viewFirstUI()
        }else{
            viewSecondUI()
        }
    }

    private fun viewFirstUI(){
        tv_signup_head.text="이메일 주소를"
        tv_signup_section1.text="이메일"
        et_signup_section1.hint="ex) mrpic@naver.com"
        et_signup_section1.setText("")
        et_signup_section1.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        ly_signup_section2.visibility = View.GONE
        viewnumber=1
    }


    private fun viewSecondUI(){
        tv_signup_head.text = "비밀번호를"
        tv_signup_section1.text = "비밀번호"
        et_signup_section1.hint = "ex) abc12345"
        et_signup_section1.setText("")
        et_signup_section1.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        et_signup_section2.setText("")
        ly_signup_section2.visibility= View.VISIBLE
        if(btn_duplicate_verification.visibility == View.VISIBLE){
            btn_duplicate_verification.visibility = View.GONE
        }
        viewnumber=2
    }

    private fun viewThirdUI(){
        tv_signup_head.text ="닉네임을"
        tv_signup_section1.text="닉네임"
        et_signup_section1.hint="동동이"
        et_signup_section1.setText("")
        et_signup_section1.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
        ly_signup_section2.visibility = View.GONE
        tv_signup_next.text ="완료"
        btn_duplicate_verification.visibility = View.VISIBLE
        viewnumber = 3
    }


}
