package jjackjjack.sopt.com.jjackjjack.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.*
import com.bumptech.glide.Glide
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.model.DonateParticipationInfo
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.textColor


class DonateParticipateListRecyclerViewAdapter(val ctx: Context, val dataList: ArrayList<DonateParticipationInfo>) : RecyclerView.Adapter<DonateParticipateListRecyclerViewAdapter.Holder>(){
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DonateParticipateListRecyclerViewAdapter.Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.li_donate, viewGroup, false )
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder : Holder, position: Int) {
        holder.d_day.text = " " + dataList[position].d_day
        holder.title.text = dataList[position].title
        holder.centerName.text = dataList[position].centerName
        holder.percent.text = dataList[position].percent
        holder.progress.progress = dataList[position].percent.toInt()
        holder.berry.text = " - " + dataList[position].berry.toString()
        if (URLUtil.isValidUrl(dataList[position].thumbnail)) {
            Glide.with(ctx).load(dataList[position].thumbnail).into(holder.thumbnail)
        }

        holder.txt.text = "베리 기부"

        var state = 0
        when (state) {
            0 -> {
                holder.percent_mark.textColor = R.color.red
                holder.percent.textColor = R.color.red
                holder.state.backgroundColor = R.color.red
                holder.state.text = "진행중"
            }
            1 -> {
                holder.percent_mark.textColor = R.color.green
                holder.percent.textColor = R.color.green
                holder.state.backgroundColor = R.color.green
                holder.state.text = "마감"
            }
            2 -> {
                holder.percent_mark.textColor = R.color.blue
                holder.percent.textColor = R.color.blue
                holder.state.backgroundColor = R.color.blue
                holder.state.text = "전달 완료"
            }
        }
    }
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var container = itemView.findViewById(R.id.li_donate_container) as RelativeLayout
        var thumbnail = itemView.findViewById(R.id.li_donate_img_url) as ImageView
        var d_day = itemView.findViewById(R.id.li_donate_d_day) as TextView
        var title = itemView.findViewById(R.id.li_donate_title) as TextView
        var centerName = itemView.findViewById(R.id.li_donate_association) as TextView
        var percent = itemView.findViewById(R.id.li_donate_percent) as TextView
        var percent_mark = itemView.findViewById(R.id.li_donate_percent_mark) as TextView
        var berry = itemView.findViewById(R.id.li_donate_berry_num) as TextView
        var progress = itemView.findViewById(R.id.li_donate_progress) as ProgressBar
        var state = itemView.findViewById(R.id.li_donate_state) as TextView

        var txt = itemView.findViewById(R.id.txt_berry_or_berry_donate) as TextView
    }
}
