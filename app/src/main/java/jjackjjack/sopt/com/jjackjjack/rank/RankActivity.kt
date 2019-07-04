package jjackjjack.sopt.com.jjackjjack.rank

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.widget.Toast
import com.example.kmj.imageslider.RankBtnAdapter
import com.example.kmj.imageslider.RankBtnItem
import com.example.kmj.imageslider.RankImgAdapter
import jjackjjack.sopt.com.jjackjjack.*
import jjackjjack.sopt.com.jjackjjack.berrycharge.BerryChargeActivity
import jjackjjack.sopt.com.jjackjjack.donaterecord.DonateRecordActivity
import jjackjjack.sopt.com.jjackjjack.mypage.MyPageActivity
import kotlinx.android.synthetic.main.activity_ranking.*
import kotlinx.android.synthetic.main.content_activity_ranking.*
import kotlinx.android.synthetic.main.nav_drawer.*
import kotlinx.android.synthetic.main.toolbar_with_hamburger.*
import org.jetbrains.anko.startActivity


class RankActivity : AppCompatActivity() {

    var RankImageList = arrayListOf<RankImgItem>(
        RankImgItem("00"),
        RankImgItem("01"),
        RankImgItem("02"),
        RankImgItem("03"),
        RankImgItem("04"),
        RankImgItem("05"),
        RankImgItem("06")
    )
    var RankBtnList = arrayListOf<RankBtnItem>(
        RankBtnItem("전체"),
        RankBtnItem("동물"),
        RankBtnItem("환경"),
        RankBtnItem("어린이"),
        RankBtnItem("장애우"),
        RankBtnItem("긴급구조"),
        RankBtnItem("어르신")
    )

    var RankingList = arrayListOf<RankingItem>(
        RankingItem("1", "1", "1", "1", "1"),
        RankingItem("2", "2", "2", "2", "2"),
        RankingItem("3", "3", "3", "3", "3"),
        RankingItem("4", "4", "4", "4", "4")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)
        initialUI()
        //이거 나중에 함수로 빼주기!

        val mAdapter = RankImgAdapter(this, RankImageList) { dog ->
            Toast.makeText(this, "number is ${dog.photo}", Toast.LENGTH_SHORT).show()
        }
        val mAdapter2 = RankBtnAdapter(this, RankBtnList) { btn ->
            Toast.makeText(this, "${btn.btnText}", Toast.LENGTH_SHORT).show()
        }
        /* 람다식{(RankImgItem) -> Unit} 부분을 추가하여 itemView의 setOnClickListener 에서 어떤 액션을 취할 지 설정해준다. */
        val mAdapter3 = RankingAdapter(this, RankingList) { rank ->
            Toast.makeText(this, "${rank.rank}", Toast.LENGTH_SHORT).show()
        }

        mRecyclerView.adapter = mAdapter

        mRecyclerView2.adapter = mAdapter2

        mRecyclerView3.adapter = mAdapter3

        val lm = LinearLayoutManager(this)
        mRecyclerView.layoutManager = lm
        lm.setOrientation(LinearLayoutManager.HORIZONTAL)
        mRecyclerView.setHasFixedSize(true)

        val lm2 = LinearLayoutManager(this)
        mRecyclerView2.layoutManager = lm2
        lm2.setOrientation(LinearLayoutManager.HORIZONTAL)
        mRecyclerView2.setHasFixedSize(true)

        val lm3 = LinearLayoutManager(this)
        mRecyclerView3.layoutManager = lm3
        lm3.setOrientation(LinearLayoutManager.VERTICAL)
        mRecyclerView3.setHasFixedSize(true)


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