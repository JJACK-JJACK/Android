package com.example.kmj.imageslider

import android.content.Context
import android.os.SystemClock
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ImageView
import com.bumptech.glide.Glide
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.activities.deliveryreview.BerryReviewActivity
import jjackjjack.sopt.com.jjackjjack.model.DeliveryReviewImageInfo
import org.jetbrains.anko.startActivity

class DeliveryReviewImageAdapter(val context: Context, val deliveryReviewImageInfoList: ArrayList<DeliveryReviewImageInfo>) : RecyclerView.Adapter<DeliveryReviewImageAdapter.Holder>() {
    private var mLastClickTime:Long = 0

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.rank_image_item, p0, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(deliveryReviewImageInfoList[position], context)
        if(URLUtil.isValidUrl(deliveryReviewImageInfoList[position].thumbnail)){
            Glide.with(context).load(deliveryReviewImageInfoList[position].thumbnail).into(holder.thumbnail)
        }

        holder.thumbnail.setOnClickListener {
            if(SystemClock.elapsedRealtime()-mLastClickTime < 2000){
                return@setOnClickListener
            }
            mLastClickTime = SystemClock.elapsedRealtime()
            //Toast.makeText(this, "number is ${rankimg.thumbnail}", Toast.LENGTH_SHORT).show()
            context.startActivity<BerryReviewActivity>("clickedItemIndex" to position)
            Log.d("idx", position.toString())
        }
    }

/* (1) Adapter의 파라미터에 람다식 itemClick을 넣는다. */

    override fun getItemCount(): Int {
        return deliveryReviewImageInfoList.size
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        /* (2) Holder에서 클릭에 대한 처리를 할 것이므로, Holder의 파라미터에 람다식 itemClick을 넣는다. */

        val thumbnail = itemView?.findViewById(R.id.PhotoImg) as ImageView
        fun bind (deliveryReviewImageInfo: DeliveryReviewImageInfo, context: Context) {

            /* (3) itemView가 클릭됐을 때 처리할 일을 itemClick으로 설정한다.
             (DeliveryReviewImageInfo) -> Unit 에 대한 함수는 나중에 MainActivity.kt 클래스에서 작성한다. */
        }
    }
}