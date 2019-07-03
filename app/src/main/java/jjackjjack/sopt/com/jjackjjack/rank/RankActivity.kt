package com.example.kmj.imageslider

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import jjackjjack.sopt.com.jjackjjack.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_ranking.*

class RankActivity : AppCompatActivity() {

    var RankImageList = arrayListOf<RankImgItem>(
            RankImgItem("dog00"),
            RankImgItem( "dog01"),
            RankImgItem("dog02"),
            RankImgItem("dog03"),
            RankImgItem("dog04"),
            RankImgItem("dog05"),
            RankImgItem("dog06")
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        val mAdapter = RankImgAdapter(this, RankImageList) { dog ->
            Toast.makeText(this, "number is ${dog.photo}", Toast.LENGTH_SHORT).show()
        }
        val mAdapter2 = RankBtnAdapter(this, RankBtnList){ btn ->
            Toast.makeText(this, "${btn.btnText}", Toast.LENGTH_SHORT).show()
        }
        /* 람다식{(RankImgItem) -> Unit} 부분을 추가하여 itemView의 setOnClickListener 에서 어떤 액션을 취할 지 설정해준다. */

        mRecyclerView.adapter = mAdapter

        mRecyclerView2.adapter = mAdapter2

        val lm = LinearLayoutManager(this)
        mRecyclerView.layoutManager = lm
        lm.setOrientation(LinearLayoutManager.HORIZONTAL)
        mRecyclerView.setHasFixedSize(true)

        val lm2 = LinearLayoutManager(this)
        mRecyclerView2.layoutManager = lm2
        lm2.setOrientation(LinearLayoutManager.HORIZONTAL)
        mRecyclerView2.setHasFixedSize(true)
    }
}
