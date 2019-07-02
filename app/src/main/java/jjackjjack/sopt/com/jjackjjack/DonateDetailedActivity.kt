package jjackjjack.sopt.com.jjackjjack

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_donate_detailed.*

class DonateDetailedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate_detailed)

        var main_adapter = DonateDetailedPagerAdapter(supportFragmentManager)
        donate_detailed_pager.adapter = main_adapter
        donate_detailed_tab.setupWithViewPager(donate_detailed_pager)

        donate_detailed_tab.getTabAt(0)?.setText("기부스토리")
        donate_detailed_tab.getTabAt(1)?.setText("사용계획")

    }
}
