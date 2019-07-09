package jjackjjack.sopt.com.jjackjjack.activities.payment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.activities.MainActivity
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import kotlinx.android.synthetic.main.activity_payment_finish.*
import org.jetbrains.anko.startActivity
import java.text.DecimalFormat

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_finish)

        val dec = DecimalFormat("#,000")
        val credits = dec.format(intent.getIntExtra("berry_charge",0))

         tv_payment_money.setText(credits)

        btn_payment_ok.setOnClickListener {
            startActivity<MainActivity>()
        }
    }

}