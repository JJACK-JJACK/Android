package jjackjjack.sopt.com.jjackjjack.mypage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import jjackjjack.sopt.com.jjackjjack.donaterecord.DonateRecordActivity
import jjackjjack.sopt.com.jjackjjack.MainActivity
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.berrycharge.BerryChargeActivity
import jjackjjack.sopt.com.jjackjjack.rank.RankActivity
import kotlinx.android.synthetic.main.activity_my_page.*
import kotlinx.android.synthetic.main.nav_drawer.*
import kotlinx.android.synthetic.main.toolbar_with_hamburger.*
import org.jetbrains.anko.startActivity

class MyPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)
        initialUI()
    }

    private fun initialUI(){
        btn_home.setOnClickListener {
            startActivity<MainActivity>()
            finish()
        }
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

    private fun drawerUI() {
        btn_hambuger.setOnClickListener {
            if (!ly_drawer.isDrawerOpen(Gravity.END)) {
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
            //finish()
        }

        btn_cancle.setOnClickListener {
            if (ly_drawer.isDrawerOpen(Gravity.END)) {
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
            finish()
        }

        btn_drawer_berrycharge.setOnClickListener {
            startActivity<BerryChargeActivity>()
            ly_drawer.closeDrawer(Gravity.END)
            // finish()
        }


    }
}
