package jjackjjack.sopt.com.jjackjjack

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_donate.*

class DonateActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)

        var main_adapter = DonateCategoryPagerAdapter(supportFragmentManager)
        donate_pager.adapter = main_adapter

        donate_tab.setupWithViewPager(donate_pager)

        donate_tab.getTabAt(0)?.setText("어린이")
        donate_tab.getTabAt(1)?.setText("어르신")
        donate_tab.getTabAt(2)?.setText("동물")
        donate_tab.getTabAt(3)?.setText("장애인")
        donate_tab.getTabAt(4)?.setText("환경")
        donate_tab.getTabAt(5)?.setText("긴급구조")


    }




}
