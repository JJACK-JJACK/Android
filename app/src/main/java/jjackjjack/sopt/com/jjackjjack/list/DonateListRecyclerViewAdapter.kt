package jjackjjack.sopt.com.jjackjjack.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import jjackjjack.sopt.com.jjackjjack.donate.DonateDetailedActivity
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.data.DonateInfoData
import org.jetbrains.anko.startActivity

//세번쨰 변수 수정 부분
class DonateListRecyclerViewAdapter (val ctx: Context, var dataList: ArrayList<DonateInfoData>, val isDonateHistory: Boolean): RecyclerView.Adapter<DonateListRecyclerViewAdapter.Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.li_donate, viewGroup, false )
        return Holder(view)

    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.d_day.text = " " + dataList[position].d_day
        holder.title.text = dataList[position].title
        holder.association.text = dataList[position].association
        holder.percent.text = dataList[position].percent
        holder.progress.progress = dataList[position].percent.toInt()
        holder.berry_num.text = dataList[position].berry_num

        holder.container.setOnClickListener {
            ctx.startActivity<DonateDetailedActivity>(
                "title" to dataList[position].title,
                "association" to dataList[position].association,
                "percent" to dataList[position].percent,
                "berry_num" to dataList[position].berry_num,
                "d_day" to dataList[position].d_day,
                "isDonateHistory" to isDonateHistory
            )
        }
    }

    inner class Holder(itemView : View): RecyclerView.ViewHolder(itemView){
        var img_url = itemView.findViewById(R.id.li_donate_img_url) as ImageView
        var container = itemView.findViewById(R.id.li_donate_container) as RelativeLayout
        var d_day = itemView.findViewById(R.id.li_donate_d_day) as TextView
        var title = itemView.findViewById(R.id.li_donate_title) as TextView
        var association = itemView.findViewById(R.id.li_donate_association) as TextView
        var percent = itemView.findViewById(R.id.li_donate_percent) as TextView
        var berry_num = itemView.findViewById(R.id.li_donate_berry_num) as TextView
        var progress = itemView.findViewById(R.id.li_donate_progress) as ProgressBar
    }
}