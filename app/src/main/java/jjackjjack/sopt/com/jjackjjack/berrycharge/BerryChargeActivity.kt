package jjackjjack.sopt.com.jjackjjack.berrycharge

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jjackjjack.sopt.com.jjackjjack.R
import kotlinx.android.synthetic.main.activity_berry_charge.*
import org.jetbrains.anko.startActivity

class BerryChargeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_berry_charge)
        initialUI()
    }

    private fun initialUI(){
        btn_berry_charge_npb.setOnClickListener {
            startActivity<DepositActivity>()
        }
    }
}
