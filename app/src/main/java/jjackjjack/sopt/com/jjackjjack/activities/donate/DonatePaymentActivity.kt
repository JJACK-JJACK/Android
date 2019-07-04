package jjackjjack.sopt.com.jjackjjack.activities.donate

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import jjackjjack.sopt.com.jjackjjack.R
import kotlinx.android.synthetic.main.activity_donate_payment.*

class DonatePaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate_payment)





        btn_donate.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_donate_payment, null)
            val dialogbutton = dialogView.findViewById<Button>(R.id.btn_close)
            builder.setView(dialogView)
            builder.show()
            dialogbutton.setOnClickListener{
                finish()
            }
        }


    }
}
