package jjackjjack.sopt.com.jjackjjack.activities.berrycharge

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.interfaces.onDrawer
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
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

        btn_charge_back.setOnClickListener {
            finish()
        }
    }
}
