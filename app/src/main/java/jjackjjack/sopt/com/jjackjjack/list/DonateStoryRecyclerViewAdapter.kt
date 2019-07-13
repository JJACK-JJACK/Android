package jjackjjack.sopt.com.jjackjjack.list

import android.content.Context
import android.media.Image
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.model.DonateStory
import jjackjjack.sopt.com.jjackjjack.network.data.StoryData

class DonateStoryRecyclerViewAdapter (val ctx: Context, val dataList: ArrayList<StoryData>):RecyclerView.Adapter<DonateStoryRecyclerViewAdapter.Holder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.li_story, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var textList : Array<TextView> = arrayOf(holder.text1, holder.text2)

        holder.subtitle.text = dataList[position].subTitle
        if(URLUtil.isValidUrl(dataList[position].img)){
            Glide.with(ctx).load(dataList[position].img).into(holder.img)
        }

        for(i in 0 until dataList[position].content.size){
            textList[i].text = dataList[position].content[i]
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var subtitle = itemView.findViewById(R.id.li_tv_subtitle) as TextView
        var text1 = itemView.findViewById(R.id.li_tv_text1) as TextView
        var text2 = itemView.findViewById(R.id.li_tv_text2) as TextView
        var img = itemView.findViewById(R.id.li_iv_img) as ImageView
    }
}