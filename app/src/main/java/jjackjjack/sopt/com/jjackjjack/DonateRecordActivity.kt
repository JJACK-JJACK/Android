package jjackjjack.sopt.com.jjackjjack

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import jjackjjack.sopt.com.jjackjjack.data.DonateInfoData
import jjackjjack.sopt.com.jjackjjack.rank.RankActivity
import kotlinx.android.synthetic.main.activity_donate_record.*
import kotlinx.android.synthetic.main.content_activity_donate_record.*
import kotlinx.android.synthetic.main.nav_drawer.*
import kotlinx.android.synthetic.main.toolbar_with_hamburger.*
import org.jetbrains.anko.startActivity


class DonateRecordActivity : AppCompatActivity() {

    lateinit var donateListRecyclerViewAdapter: DonateListRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate_record)
        initialUI()
        var dataList: ArrayList<DonateInfoData> = ArrayList()

        dataList.add(
            DonateInfoData(
                "64", "병제에게 맛있는 한끼를", "솝트", "99", "150.000"
            )
        )
        dataList.add(
            DonateInfoData(
                "15", "동진에게 맛있는 한끼를", "솝트", "55", "199.999"
            )
        )
        dataList.add(
            DonateInfoData(
                "33", "연수에게 맛있는 한끼를", "솝트", "20", "130.000"
            )
        )

        donateListRecyclerViewAdapter = DonateListRecyclerViewAdapter(this, dataList, true)
        rv_donate_record.adapter = donateListRecyclerViewAdapter
        rv_donate_record.layoutManager = LinearLayoutManager(this)
    }

    private fun initialUI(){
        btn_home.setOnClickListener {
            startActivity<MainActivity>()
            finish()
        }
        drawerUI()
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
