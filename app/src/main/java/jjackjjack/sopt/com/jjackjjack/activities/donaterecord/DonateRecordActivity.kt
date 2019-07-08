package jjackjjack.sopt.com.jjackjjack.activities.donaterecord

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import jjackjjack.sopt.com.jjackjjack.activities.MainActivity
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.activities.berrycharge.BerryChargeActivity
import jjackjjack.sopt.com.jjackjjack.activities.donate.DonateActivity
import jjackjjack.sopt.com.jjackjjack.model.DonateInfo
import jjackjjack.sopt.com.jjackjjack.list.DonateListRecyclerViewAdapter
import jjackjjack.sopt.com.jjackjjack.activities.mypage.MyPageActivity
import jjackjjack.sopt.com.jjackjjack.activities.rank.RankActivity
import jjackjjack.sopt.com.jjackjjack.interfaces.onDrawer
import jjackjjack.sopt.com.jjackjjack.utillity.Constants
import kotlinx.android.synthetic.main.activity_donate_record.ly_drawer
import kotlinx.android.synthetic.main.content_activity_donate_record.*
import kotlinx.android.synthetic.main.nav_drawer.*
import kotlinx.android.synthetic.main.toolbar_with_hamburger.*
import org.jetbrains.anko.startActivity


class DonateRecordActivity : AppCompatActivity(), onDrawer {

    lateinit var btnFset: Array<ImageView>

    lateinit var btnAset: Array<View>

    lateinit var actSet : Array<Class<out AppCompatActivity>>

    lateinit var donateListRecyclerViewAdapter: DonateListRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate_record)
        initialUI()

    }

    private fun initialUI(){


        btn_home.setOnClickListener {
            startActivity<MainActivity>()
            finish()
        }
        drawerUI()

        var list: ArrayList<DonateInfo> = ArrayList()

//        list.add(
//            DonateInfo(
//                "64", "병제에게 맛있는 한끼를", "솝트", "99", "150.000"
//            )
//        )
//        list.add(
//            DonateInfo(
//                "15", "동진에게 맛있는 한끼를", "솝트", "55", "199.999"
//            )
//        )
//        list.add(w
//            DonateInfo(
//                "33", "연수에게 맛있는 한끼를", "솝트", "20", "130.000"
//            )
//        )

        donateListRecyclerViewAdapter = DonateListRecyclerViewAdapter(this, list, true)
        rv_donate_record.adapter = donateListRecyclerViewAdapter
        rv_donate_record.layoutManager = LinearLayoutManager(this)
    }

    override fun drawerUI() {
        actSet = arrayOf(
            MainActivity::class.java, DonateRecordActivity::class.java,
            RankActivity::class.java, MyPageActivity::class.java,
            BerryChargeActivity::class.java
        )

        btnFset = arrayOf( //프래그먼트로 가는 버튼
            btn_drawer_f_child, btn_drawer_f_senior, btn_drawer_f_animal, btn_drawer_f_disabled,
            btn_drawer_f_env, btn_drawer_f_emergency
        )

        btnAset = arrayOf(
            btn_drawer_home, btn_drawer_donate_record, btn_drawer_rank,
            btn_drawer_mypage, btn_drawer_berrycharge
        )

        drawerBtnSetting(Constants.ACTIVITY_DONATE_RECORD)
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
                ly_drawer.closeDrawer(Gravity.END)
                startActivity(intent)
                finish()
            }
        }

        for(i in 0 until btnFset.size){
            btnFset[i].setOnClickListener {
                startActivity<DonateActivity>("fragment" to i)
                Handler().postDelayed({ly_drawer.closeDrawer(Gravity.END)}, 110)
                finish()

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