package jjackjjack.sopt.com.jjackjjack.activities.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jjackjjack.sopt.com.jjackjjack.activities.MainActivity
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController
import kotlinx.android.synthetic.main.activity_beginning.*
import org.jetbrains.anko.startActivity

class BeginningActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beginning)

        initialUI()

        if(SharedPreferenceController.getUserID(this).isNotEmpty()){
            startActivity<MainActivity>()
            finish()
        }


    }


    private fun initialUI(){

        btn_beginning_login.setOnClickListener {
            startActivity<LoginActivity>()
        }

        btn_beginning_signup.setOnClickListener {
            startActivity<SignUpActivity>()
        }

    }
}
