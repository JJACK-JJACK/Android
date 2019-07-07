package jjackjjack.sopt.com.jjackjjack.activities.mypage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jjackjjack.sopt.com.jjackjjack.R
import kotlinx.android.synthetic.main.activity_my_page_nickname_modify.*
import android.content.Intent



class MyPageNicknameModifyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page_nickname_modify)

        edt_nickname_modify.hint = intent.getStringExtra("nickname")

        btn_nick_back.setOnClickListener {
            finish()
        }

        btn_erase_all.setOnClickListener {
            edt_nickname_modify.setText(null)
        }

        btn_check.setOnClickListener {
            var edtString = edt_nickname_modify.text.toString()
            if(edtString ==  ""){
                edtString = intent.getStringExtra("nickname")
            }
            val resultIntent = Intent()
            resultIntent.putExtra("nickname_changed", edtString)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
