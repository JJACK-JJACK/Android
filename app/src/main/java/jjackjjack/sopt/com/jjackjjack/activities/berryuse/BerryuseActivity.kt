package jjackjjack.sopt.com.jjackjjack.activities.berryuse

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.example.kmj.imageslider.RankBtnItem
import com.example.kmj.imageslider.RankImgAdapter
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.activities.rank.RankingAdapter
import jjackjjack.sopt.com.jjackjjack.activities.rank.RankingItem
import jjackjjack.sopt.com.jjackjjack.data.DonateUsePlanData
import kotlinx.android.synthetic.main.activity_berry_deposit.*
import kotlinx.android.synthetic.main.activity_rank_berryreivew.*
import kotlinx.android.synthetic.main.content_activity_ranking.*
import kotlinx.android.synthetic.main.fragment_berryuse_review.*
import jjackjjack.sopt.com.jjackjjack.list.DonateUsePlanRecyclerViewAdapter as DonateUsePlanRecyclerViewAdapter1

class BerryuseActivity : AppCompatActivity() {
    var BerryUseList = arrayListOf<BerryUseItem>(
        BerryUseItem(
            "1", "입양지원 활동 및 입양진행", "30.000"
        ),
        BerryUseItem(
            "2", "위급한 유기견 대상 영양제 지원, 예방접종 진행", "19.000"
        ),
        BerryUseItem(
            "3", "약품 및 물품비용", "23.000"
        )

    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rank_berryreivew)

       initialUI()
    }

    private fun initialUI(){
        val berryuseAdapter = BerryUseAdapter(this, BerryUseList)

        berry_use_detailed.adapter = berryuseAdapter

        val lm = LinearLayoutManager(this)
        berry_use_detailed.layoutManager = lm
        lm.setOrientation(LinearLayoutManager.VERTICAL)
        berry_use_detailed.setHasFixedSize(true)
    }

}
