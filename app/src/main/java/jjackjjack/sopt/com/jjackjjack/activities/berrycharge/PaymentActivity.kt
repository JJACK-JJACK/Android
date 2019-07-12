package jjackjjack.sopt.com.jjackjjack.activities.berrycharge

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.activities.home.MainActivity
import kotlinx.android.synthetic.main.activity_payment_finish.*
import org.jetbrains.anko.startActivity
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_finish)

        val dec = DecimalFormat("#,000")
        val credits = dec.format(intent.getIntExtra("berry_charge", 0))

        val get_selected_bank = intent.getStringExtra("selected_bank")
        val set_selected_bank = get_selected_bank.split(" ")

        val cal = Calendar.getInstance()
        cal.time = Date()
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        cal.add(Calendar.DATE, 1)

        tv_payment_money.setText(credits+"원")
        tv_payment_bank.setText(set_selected_bank[0])
        tv_payment_account.setText(set_selected_bank[1])
        tv_payment_date.setText(df.format(cal.time).toString()+" 까지")

        btn_payment_ok.setOnClickListener {
            startActivity<MainActivity>()
            finish()
        }
    }
    private fun converteDday(finish: String) : String{

        var dday : Int = 0
        var Dday: String = ""


        if(finish != null) {

            val today = Calendar.getInstance()
            val finishdateFormat = SimpleDateFormat("yyyy-MM-dd").parse(finish.split("T")[0])
            val instance: Calendar = Calendar.getInstance()
            instance.setTime(finishdateFormat)


            val cnt_today: Long = today.timeInMillis / 86400000
            val cnt_instance: Long = instance.timeInMillis / 86400000

            val sub: Long = cnt_today - cnt_instance

            dday = sub.toInt() + 1

            if(dday >0){
                Dday = "+$dday"
            }
            else{
                Dday = "$dday"
            }
        }
        return Dday

    }

}