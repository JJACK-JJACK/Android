package jjackjjack.sopt.com.jjackjjack.activities.stamp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import jjackjjack.sopt.com.jjackjjack.R
import kotlinx.android.synthetic.main.activity_get_berry.*
import android.support.v4.os.HandlerCompat.postDelayed
import android.view.View


class GetBerryActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_berry)

        initialUI()
        val handler = Handler()
        handler.postDelayed(Runnable { txt_get_berry.visibility = View.VISIBLE }, 1700)
    }

    private fun initialUI(){
        reward_berry.text = intent.getIntExtra("rewardBerry", 0).toString()

        btn_toolbar_back.setOnClickListener {
            finish()
        }
        btn_check.setOnClickListener {
            finish()
        }

        animation_get_berry.setAnimation("berrydata3.json")
        animation_get_berry.playAnimation()
    }
}
