package jjackjjack.sopt.com.jjackjjack.rank

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.kmj.imageslider.RankBtnAdapter
import com.example.kmj.imageslider.RankBtnItem
import com.example.kmj.imageslider.RankImgAdapter
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.rank.RankImgItem
import jjackjjack.sopt.com.jjackjjack.rank.RankingAdapter
import jjackjjack.sopt.com.jjackjjack.rank.RankingItem
import kotlinx.android.synthetic.main.activity_ranking.*

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
}