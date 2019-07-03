package com.example.kmj.imageslider

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import jjackjjack.sopt.com.jjackjjack.R

class RankBtnAdapter(val context2: Context, val rankBtnItemList: ArrayList<RankBtnItem>, val itemClick:(RankBtnItem)->Unit): RecyclerView.Adapter<RankBtnAdapter.Holder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RankBtnAdapter.Holder {
        val view = LayoutInflater.from(context2).inflate(R.layout.rank_button_item, p0, false)
        return  Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return rankBtnItemList.size
    }

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        p0?.bind(rankBtnItemList[p1],context2)
    }

    inner class Holder(itemView: View?, itemClick: (RankBtnItem)->Unit):RecyclerView.ViewHolder(itemView!!){
        val rankBtn = itemView?.findViewById<TextView>(R.id.rankBtn)

        fun bind(rankBtnItem:RankBtnItem, context: Context){
            if(rankBtnItem.btnText !=""){
                val resourceId = context.resources.getIdentifier(rankBtnItem.btnText, "drawable", context.packageName)
                /*if (rankBtn != null) {
                    rankBtn.setText(resourceId)
                }*/
                //클릭됐을 때 처리할 일을 itemclick으로 설정,
                //(RankBtnItem)->Unit에 대한 함수는 나중에 rankactivity에서 작성
                itemView.setOnClickListener{itemClick(rankBtnItem)}
            }
            rankBtn?.text = rankBtnItem.btnText
        }
    }

}