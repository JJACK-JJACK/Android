package jjackjjack.sopt.com.jjackjjack.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jjackjjack.sopt.com.jjackjjack.MainActivity
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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
