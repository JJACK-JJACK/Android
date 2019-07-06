package jjackjjack.sopt.com.jjackjjack.activities.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import jjackjjack.sopt.com.jjackjjack.activities.MainActivity
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import android.view.MotionEvent


class LoginActivity : AppCompatActivity() {

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

    private fun initialUI(){
        btn_login_login.setOnClickListener {
            val input_email: String = et_login_email.text.toString()
            val input_pw: String = et_login_pw.text.toString()


            if(input_email.isNotEmpty() && input_pw.isNotEmpty()){

                //이거 나중에 서버하고 통신 필요 지금은 누르면 바로 로그인됨
                SharedPreferenceController.setUserEmail(this, input_email)
                SharedPreferenceController.setUserPW(this, input_pw)


                startActivity<MainActivity>()
                finish()
            }


        }


        btn_login_signup.setOnClickListener {
            startActivity<SignUpActivity>()
        }
    }

}
