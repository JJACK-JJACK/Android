package jjackjjack.sopt.com.jjackjjack.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.activities.berrycharge.BerryChargeActivity
import jjackjjack.sopt.com.jjackjjack.activities.donate.DonateActivity
import jjackjjack.sopt.com.jjackjjack.activities.donaterecord.DonateRecordActivity
import jjackjjack.sopt.com.jjackjjack.activities.mypage.MyPageActivity
import jjackjjack.sopt.com.jjackjjack.activities.rank.RankActivity
import jjackjjack.sopt.com.jjackjjack.utillity.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_activity_main.*
import kotlinx.android.synthetic.main.nav_drawer.*
import org.jetbrains.anko.startActivity







class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fragmentAdapter = MainActivityImageSliderAdapter(supportFragmentManager)
        main_activity_slider_pager.adapter = fragmentAdapter

        main_activity_slider_pager.setClipToPadding(false)
        val dpValue = 40
        val d = resources.displayMetrics.density
        val margin = (dpValue * d).toInt()
        main_activity_slider_pager.setPadding(margin, 0, margin, 0)
        main_activity_slider_pager.setPageMargin(margin / 2)

        for (i in 0..5) {
            val fragmentMainActivityImageSlider = FragmentMainActivityImageSlider()
            fragmentAdapter.addImage(fragmentMainActivityImageSlider)
        }
        fragmentAdapter.notifyDataSetChanged()

        dot_indicator_activity_main.setupWithViewPager(main_activity_slider_pager, true)



        initalUI()
    }



    private fun initalUI(){

        drawerUI()

    }

    override fun onBackPressed() {
        if(ly_drawer.isDrawerOpen(Gravity.END)){
            ly_drawer.closeDrawer(Gravity.END)
        }
        else{
            super.onBackPressed()
        }
    }

    private fun drawerUI(){
        btn_hambuger.setOnClickListener {
            if(!ly_drawer.isDrawerOpen(Gravity.END)){
                ly_drawer.openDrawer(Gravity.END)
            }
        }
        btn_drawer_donate_record.setOnClickListener {
            startActivity<DonateRecordActivity>()
            ly_drawer.closeDrawer(Gravity.END)
            //finish()
        }
        btn_drawer_home.setOnClickListener {
            startActivity<MainActivity>()
            ly_drawer.closeDrawer(Gravity.END)
            finish()
        }

        btn_cancle.setOnClickListener {
            if(ly_drawer.isDrawerOpen(Gravity.END)){
                ly_drawer.closeDrawer(Gravity.END)
            }
        }


        btn_drawer_rank.setOnClickListener {
            startActivity<RankActivity>()
            ly_drawer.closeDrawer(Gravity.END)
            //finish()
        }

        btn_drawer_mypage.setOnClickListener {
            startActivity<MyPageActivity>()
            ly_drawer.closeDrawer(Gravity.END)
            // finish()
        }

        btn_drawer_berrycharge.setOnClickListener {
            startActivity<BerryChargeActivity>()
            ly_drawer.closeDrawer(Gravity.END)
            // finish()
        }

        btn_drawer_f_animal.setOnClickListener {
            startActivity<DonateActivity>("fragment" to Constants.FRAGMENT_ANIMAL)
            ly_drawer.closeDrawer(Gravity.END)
        }

        btn_drawer_f_child.setOnClickListener {
            startActivity<DonateActivity>("fragment" to Constants.FRAGMENT_CHILD)
            ly_drawer.closeDrawer(Gravity.END)
        }

        btn_drawer_f_senior.setOnClickListener {
            startActivity<DonateActivity>("fragment" to Constants.FRAGMENT_ELDER)
            ly_drawer.closeDrawer(Gravity.END)
        }

        btn_drawer_f_disabled.setOnClickListener {
            startActivity<DonateActivity>("fragment" to Constants.FRAGMENT_DISABLE)
            ly_drawer.closeDrawer(Gravity.END)
        }

        btn_drawer_f_emergency.setOnClickListener {
            startActivity<DonateActivity>("fragment" to Constants.FRAGMENT_EMERGENCY)
            ly_drawer.closeDrawer(Gravity.END)
        }

        btn_drawer_f_env.setOnClickListener {
            startActivity<DonateActivity>("fragment" to Constants.FRAGMENT_ENVIRONMENT)
            ly_drawer.closeDrawer(Gravity.END)
        }

    }
}
