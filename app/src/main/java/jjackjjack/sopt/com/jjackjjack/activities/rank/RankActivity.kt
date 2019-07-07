package jjackjjack.sopt.com.jjackjjack.activities.rank

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.example.kmj.imageslider.RankImgAdapter
import jjackjjack.sopt.com.jjackjjack.*
import jjackjjack.sopt.com.jjackjjack.activities.MainActivity
import jjackjjack.sopt.com.jjackjjack.activities.berrycharge.BerryChargeActivity
import jjackjjack.sopt.com.jjackjjack.activities.berryreview.BerryreviewActivity
import jjackjjack.sopt.com.jjackjjack.activities.donate.DonateActivity
import jjackjjack.sopt.com.jjackjjack.activities.donaterecord.DonateRecordActivity
import jjackjjack.sopt.com.jjackjjack.activities.mypage.MyPageActivity
import jjackjjack.sopt.com.jjackjjack.interfaces.onDrawer
import jjackjjack.sopt.com.jjackjjack.utillity.Constants
import kotlinx.android.synthetic.main.activity_ranking.*
import kotlinx.android.synthetic.main.content_activity_ranking.*
import kotlinx.android.synthetic.main.nav_drawer.*
import kotlinx.android.synthetic.main.toolbar_with_hamburger.*
import org.jetbrains.anko.startActivity


class RankActivity : AppCompatActivity(), onDrawer {

    lateinit var btnFset: Array<ImageView>

    lateinit var btnAset: Array<View>

    lateinit var actSet : Array<Class<out AppCompatActivity>>


    var RankImageList = arrayListOf<RankImgItem>(
        RankImgItem("00"),
        RankImgItem("01"),
        RankImgItem("02"),
        RankImgItem("03"),
        RankImgItem("04"),
        RankImgItem("05"),
        RankImgItem("06"),
        RankImgItem("07"),
        RankImgItem("08"),
        RankImgItem("09"),
        RankImgItem("10")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)
        initialUI()
        //이거 나중에 함수로 빼주기!

        val mAdapter = RankImgAdapter(this, RankImageList) { rankimg ->
            //Toast.makeText(this, "number is ${rankimg.photo}", Toast.LENGTH_SHORT).show()
            startActivity<BerryreviewActivity>()
        }

        mRecyclerView.adapter = mAdapter

        mRecyclerView.setOnClickListener {
            startActivity<BerryreviewActivity>()
        }

        val lm = LinearLayoutManager(this)
        mRecyclerView.layoutManager = lm
        lm.setOrientation(LinearLayoutManager.HORIZONTAL)
        mRecyclerView.setHasFixedSize(true)


    }


    private fun initialUI(){
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

        drawerBtnSetting(Constants.ACTIVITY_RANK)
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