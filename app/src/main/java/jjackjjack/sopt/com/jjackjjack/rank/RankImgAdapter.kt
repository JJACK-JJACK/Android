package com.example.kmj.imageslider

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.rank.RankImgItem

class RankImgAdapter(val context: Context, val rankImgItemList: ArrayList<RankImgItem>, val itemClick: (RankImgItem) -> Unit) : RecyclerView.Adapter<RankImgAdapter.Holder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.rank_image_item, p0, false)
        return Holder(view, itemClick)
    }


    override fun onBindViewHolder(p0: Holder, p1: Int) {
        p0?.bind(rankImgItemList[p1], context)
    }
/* (1) Adapter의 파라미터에 람다식 itemClick을 넣는다. */



    override fun getItemCount(): Int {
        return rankImgItemList.size
    }
    inner class Holder(itemView: View?, itemClick: (RankImgItem) -> Unit) : RecyclerView.ViewHolder(itemView!!) {
        /* (2) Holder에서 클릭에 대한 처리를 할 것이므로, Holder의 파라미터에 람다식 itemClick을 넣는다. */

        val dogPhoto = itemView?.findViewById<ImageView>(R.id.PhotoImg)


        fun bind (rankImgItem: RankImgItem, context: Context) {
//            if (rankImgItem.photo != "") {
//                val resourceId = context.resources.getIdentifier(rankImgItem.photo, "drawable", context.packageName)
//                dogPhoto?.setImageResource(resourceId)
//            } else {
//                dogPhoto?.setImageResource(R.mipmap.ic_launcher)
//            }

            itemView.setOnClickListener { itemClick(rankImgItem) }
            /* (3) itemView가 클릭됐을 때 처리할 일을 itemClick으로 설정한다.
             (RankImgItem) -> Unit 에 대한 함수는 나중에 MainActivity.kt 클래스에서 작성한다. */
        }
    }
}