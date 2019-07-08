package jjackjjack.sopt.com.jjackjjack.activities.mypage

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jjackjjack.sopt.com.jjackjjack.R
import kotlinx.android.synthetic.main.activity_my_page_modify.*
import android.R.attr.data
import kotlinx.android.synthetic.main.activity_mypage_berryhistory.*


class MyPageModifyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page_modify)

        btn_modify_nickname.setOnClickListener {
            val intent = Intent(this, MyPageNicknameModifyActivity::class.java)
            intent.putExtra("nickname", curr_nickname.text)
            startActivityForResult(intent, 3000)
        }

        btn_back.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode === RESULT_OK) {
            when (requestCode) {
                3000 -> {
                    curr_nickname.setText(data?.getStringExtra("nickname_changed"))
                    curr_nickname2.setText(data?.getStringExtra("nickname_changed"))
                }
            }
        }
    }

}
