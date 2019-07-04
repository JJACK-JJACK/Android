package jjackjjack.sopt.com.jjackjjack

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.view.Gravity
import android.view.MenuItem
import jjackjjack.sopt.com.jjackjjack.rank.RankActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_activity_main.*
import kotlinx.android.synthetic.main.nav_drawer.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initalUI()
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initalUI(){
        btn_hambuger.setOnClickListener {
            if(!ly_drawer.isDrawerOpen(Gravity.END)){
                ly_drawer.openDrawer(Gravity.END)
            }
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

    private fun drawerUI(){
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

        btn_drawer_donate_record.setOnClickListener {
            startActivity<DonateRecordActivity>()
            ly_drawer.closeDrawer(Gravity.END)
            //finish()
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

    }
}
