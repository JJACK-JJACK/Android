package jjackjjack.sopt.com.jjackjjack.activities.donate

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.util.Log
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.activities.donate.adapter.DonateCategoryPagerAdapter
import jjackjjack.sopt.com.jjackjjack.activities.donate.fragment.*
import jjackjjack.sopt.com.jjackjjack.utillity.Constants
import kotlinx.android.synthetic.main.activity_donate.*
import kotlin.properties.Delegates

class DonateActivity : AppCompatActivity() {

    var curFragmentId : Int by Delegates.notNull()
    var curTabId : Int by Delegates.notNull()

    private val sText = arrayOf(
        "어린이", "어르신", "동물", "장애인", "환경", "긴급구조"
    )

    private val sFragmentConstant = arrayOf(Constants.FRAGMENT_CHILD, Constants.FRAGMENT_ELDER,
        Constants.FRAGMENT_ANIMAL, Constants.FRAGMENT_DISABLE, Constants.FRAGMENT_ENVIRONMENT,
        Constants.FRAGMENT_EMERGENCY)

    private val fragments = arrayOf(
        ChildFragment(), ElderFragment(), AnimalFragment(), DisabledFragment(),
        EnvironmentFragment(), EmergencyFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)

        var fragnum: Int = intent.getIntExtra("fragment", -1)

        var main_adapter = DonateCategoryPagerAdapter(supportFragmentManager)
        donate_pager.adapter = main_adapter

        donate_tab.setupWithViewPager(donate_pager)

        for(i in 0 until sFragmentConstant.size){
            donate_tab.getTabAt(sFragmentConstant[i])?.setText(sText[i])
        }

        if(fragnum != -1){
            Log.d("fragnum", fragnum.toString())
            donate_pager.setCurrentItem(fragnum)
//            val fragmentTransaction = supportFragmentManager.beginTransaction()
//            val fragment = fragments[fragnum]
//            fragmentTransaction.replace(R.id.donate_pager, fragment)
//            fragmentTransaction.commit()
        }

/*줄였음.
        donate_tab.getTabAt(Constants.FRAGMENT_CHILD)?.setText("어린이")
        donate_tab.getTabAt(Constants.FRAGMENT_ELDER)?.setText("어르신")
        donate_tab.getTabAt(Constants.FRAGMENT_ANIMAL)?.setText("동물")
        donate_tab.getTabAt(Constants.FRAGMENT_DISABLE)?.setText("장애인")
        donate_tab.getTabAt(Constants.FRAGMENT_ENVIRONMENT)?.setText("환경")
        donate_tab.getTabAt(Constants.FRAGMENT_EMERGENCY)?.setText("긴급구조")*/


    }






}
