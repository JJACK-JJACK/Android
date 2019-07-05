package jjackjjack.sopt.com.jjackjjack.activities.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import jjackjjack.sopt.com.jjackjjack.R
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.startActivity

class SignUpActivity : AppCompatActivity() {

    private var viewnumber = 1 //회원가입 1, 회원가입 2, 회원가입 3 나중에 상수로 빼서 when
    //전역변수 처리해서 input에 들어간거 넣어줘야함


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btn_back.setOnClickListener {
            onBackPressed()
        }

    }



    override fun onResume() {
        super.onResume()
        //이거 if-else가 길어질 각이 왔다 onResume으로 옮길수도
        btn_signup_next.setOnClickListener {


            if(viewnumber==1){ //아이디랑 비번 있는지 확인
                val input_email:String = et_signup_section1.text.toString()

                if(input_email.isNotEmpty()){
                   viewSecondUI()
                }
            }
            else if(viewnumber==2){
                val input_pw:String = et_signup_section1.text.toString()
                val input_pw_verification:String = et_signup_section2.text.toString()
                if(input_pw.isNotEmpty()&&input_pw_verification.isNotEmpty()&&input_pw.contentEquals(input_pw_verification)){
                   viewThirdUI()
                }

            }
            else{

                val input_nickname :String = et_signup_section1.text.toString()

                if(input_nickname.isNotEmpty()){
                    startActivity<LoginActivity>()
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
