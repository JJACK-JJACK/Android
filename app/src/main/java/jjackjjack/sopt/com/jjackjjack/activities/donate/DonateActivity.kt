package jjackjjack.sopt.com.jjackjjack.activities.donate

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.activities.MainActivity
import jjackjjack.sopt.com.jjackjjack.activities.berrycharge.BerryChargeActivity
import jjackjjack.sopt.com.jjackjjack.activities.berryuse.BerryHistoryActivity
import jjackjjack.sopt.com.jjackjjack.activities.donate.adapter.DonateCategoryPagerAdapter
import jjackjjack.sopt.com.jjackjjack.activities.donaterecord.DonateRecordActivity
import jjackjjack.sopt.com.jjackjjack.activities.mypage.MyPageActivity
import jjackjjack.sopt.com.jjackjjack.activities.rank.RankActivity
import jjackjjack.sopt.com.jjackjjack.interfaces.onDrawer
import jjackjjack.sopt.com.jjackjjack.utillity.Constants
import kotlinx.android.synthetic.main.activity_donate.*
import kotlinx.android.synthetic.main.content_activity_donate.*
import kotlinx.android.synthetic.main.nav_drawer.*
import kotlinx.android.synthetic.main.toolbar_with_hamburger.*
import org.jetbrains.anko.startActivity

class DonateActivity : AppCompatActivity(), onDrawer {

    lateinit var btnFset: Array<ImageView>

    lateinit var btnAset: Array<View>

    lateinit var actSet : Array<Class<out AppCompatActivity>>

    private val sText = arrayOf(
        "어린이", "어르신", "동물", "장애인", "환경", "긴급구조"
    )

    private val sFragmentConstant = arrayOf(Constants.FRAGMENT_CHILD, Constants.FRAGMENT_ELDER,
        Constants.FRAGMENT_ANIMAL, Constants.FRAGMENT_DISABLE, Constants.FRAGMENT_ENVIRONMENT,
        Constants.FRAGMENT_EMERGENCY)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)

        initialUI()

    }

    override fun onResume() {
        super.onResume()
        var fragnum: Int = intent.getIntExtra("fragment", -1)
        if(fragnum != -1){
            Log.d("fragnum", fragnum.toString())
            donate_pager.setCurrentItem(fragnum)

        }


    }

    private fun initialUI(){

        var main_adapter = DonateCategoryPagerAdapter(supportFragmentManager)
        donate_pager.adapter = main_adapter

        donate_tab.setupWithViewPager(donate_pager)

        for(i in 0 until sFragmentConstant.size){
            donate_tab.getTabAt(sFragmentConstant[i])?.setText(sText[i])
        }

        btn_home.setOnClickListener {
            startActivity<MainActivity>()
            finish()
        }
        drawerUI()
    }

    override fun drawerUI() {
        actSet = arrayOf(
            MainActivity::class.java, DonateRecordActivity::class.java,
            RankActivity::class.java, MyPageActivity::class.java,
            BerryChargeActivity::class.java, BerryHistoryActivity::class.java
        )

        btnFset = arrayOf( //프래그먼트로 가는 버튼
            btn_drawer_f_child, btn_drawer_f_senior, btn_drawer_f_animal, btn_drawer_f_disabled,
            btn_drawer_f_env, btn_drawer_f_emergency
        )

        btnAset = arrayOf(
            btn_drawer_home, btn_drawer_donate_record, btn_drawer_rank,
            btn_drawer_mypage, btn_drawer_berrycharge, btn_drawer_usehistory
        )
        drawerBtnSetting(Constants.ACTIVITY_DONATE)
    }

    override fun drawerBtnSetting(activityType: Int) {
        btn_hambuger.setOnClickListener {
            if(!ly_drawer.isDrawerOpen(Gravity.END)){
                ly_drawer.openDrawer(Gravity.END)
            }
        }

        btn_cancel.setOnClickListener {
            if(ly_drawer.isDrawerOpen(Gravity.END)){
                ly_drawer.closeDrawer(Gravity.END)
            }
        }


        for(i in 0 until btnAset.size){
            btnAset[i].setOnClickListener{
                val intent = Intent(this, actSet[i])
                var fm : FragmentManager = supportFragmentManager
                fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                startActivity(intent)
                ly_drawer.closeDrawer(Gravity.END)
                finish()

            }
        }

        for(i in 0 until btnFset.size){
            btnFset[i].setOnClickListener {
                donate_pager.setCurrentItem(i)
                Handler().postDelayed({ly_drawer.closeDrawer(Gravity.END)}, 200)
            }
        }

    }

    override fun onBackPressed() {
        if(ly_drawer.isDrawerOpen(Gravity.END)){
            ly_drawer.closeDrawer(Gravity.END)
        }
        else{
            super.onBackPressed()
        }
    }

}
