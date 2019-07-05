package jjackjjack.sopt.com.jjackjjack.activities.donate

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.activities.donate.adapter.DonateCategoryPagerAdapter
import jjackjjack.sopt.com.jjackjjack.utillity.Constants
import kotlinx.android.synthetic.main.activity_donate.*

class DonateActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)

        var main_adapter =
            DonateCategoryPagerAdapter(supportFragmentManager)
        donate_pager.adapter = main_adapter

        donate_tab.setupWithViewPager(donate_pager)

        donate_tab.getTabAt(Constants.FRAGMENT_CHILD)?.setText("어린이")
        donate_tab.getTabAt(Constants.FRAGMENT_ELDER)?.setText("어르신")
        donate_tab.getTabAt(Constants.FRAGMENT_ANIMAL)?.setText("동물")
        donate_tab.getTabAt(Constants.FRAGMENT_DISABLE)?.setText("장애인")
        donate_tab.getTabAt(Constants.FRAGMENT_ENVIRONMENT)?.setText("환경")
        donate_tab.getTabAt(Constants.FRAGMENT_EMERGENCY)?.setText("긴급구조")

        var fragnum: Int = intent.getIntExtra("fragment", 0)
        if(fragnum != 0){
            Log.d("fragnum", fragnum.toString())
            main_adapter.getItem(fragnum)
        }

    }





}
