package com.example.kmj.imageslider

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ImageView
import com.bumptech.glide.Glide
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.activities.berryreview.BerryreviewActivity
import jjackjjack.sopt.com.jjackjjack.activities.rank.RankImgItem
import org.jetbrains.anko.startActivity

class RankImgAdapter(val context: Context, val rankImgItemList: ArrayList<RankImgItem>) : RecyclerView.Adapter<RankImgAdapter.Holder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.rank_image_item, p0, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(rankImgItemList[position], context)
//        if(URLUtil.isValidUrl(rankImgItemList[position].thumbnail)){
//            Glide.with(context).load(rankImgItemList[position].thumbnail).into(holder.thumbnail)
//        }

        holder.thumbnail.setOnClickListener {
            //Toast.makeText(this, "number is ${rankimg.thumbnail}", Toast.LENGTH_SHORT).show()
            context.startActivity<BerryreviewActivity>("clickedItemIndex" to position)
            Log.d("idx", position.toString())
        }
    }

/* (1) Adapter의 파라미터에 람다식 itemClick을 넣는다. */

    override fun getItemCount(): Int {
        return rankImgItemList.size
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        /* (2) Holder에서 클릭에 대한 처리를 할 것이므로, Holder의 파라미터에 람다식 itemClick을 넣는다. */

        val thumbnail = itemView?.findViewById(R.id.PhotoImg) as ImageView
        fun bind (rankImgItem: RankImgItem, context: Context) {

            /* (3) itemView가 클릭됐을 때 처리할 일을 itemClick으로 설정한다.
             (RankImgItem) -> Unit 에 대한 함수는 나중에 MainActivity.kt 클래스에서 작성한다. */
        }
    }
}