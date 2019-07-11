package jjackjjack.sopt.com.jjackjjack.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import jjackjjack.sopt.com.jjackjjack.R
import kotlinx.android.synthetic.main.activity_get_berry.*

class GetBerryActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_berry)


        animation_get_berry.setAnimation("berrydata.json")
        animation_get_berry.playAnimation()
    }
}
