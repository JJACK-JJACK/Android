package jjackjjack.sopt.com.jjackjjack.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jjackjjack.sopt.com.jjackjjack.activities.donate.DonateDetailedActivity
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.activities.donaterecord.DonateRecordStatusActivity
import jjackjjack.sopt.com.jjackjjack.model.DonateInfo
import jjackjjack.sopt.com.jjackjjack.network.data.DonateSortedData
import org.jetbrains.anko.startActivity

//세번쨰 변수 수정 부분
class DonateListRecyclerViewAdapter (val ctx: Context, var list: ArrayList<DonateInfo>, val isDonateHistory: Boolean): RecyclerView.Adapter<DonateListRecyclerViewAdapter.Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.li_donate, viewGroup, false )
        return Holder(view)

    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.d_day.text = " " + list[position].d_day
        holder.title.text = list[position].title
        holder.association.text = list[position].centerName
        holder.percent.text = list[position].percent
        holder.progress.progress = list[position].percent.toInt()
        holder.berry_num.text = list[position].maxBerry
        if(URLUtil.isValidUrl(list[position].thumnail)){
            Glide.with(ctx).load(list[position].thumnail).into(holder.thumbnail)
        }

        if (isDonateHistory){
            holder.container.setOnClickListener {
                ctx.startActivity<DonateRecordStatusActivity>(
                    "title" to list[position].title,
                    "centerName" to list[position].centerName,
                    "percent" to list[position].percent,
                    "maxBerry" to list[position].maxBerry,
                    "d_day" to list[position].d_day
                    //"isDonateHistory" to isDonateHistory
                )
            }
        }
        else if (!isDonateHistory){
            holder.container.setOnClickListener {
                ctx.startActivity<DonateDetailedActivity>(
                    "title" to list[position].title,
                    "centerName" to list[position].centerName,
                    "percent" to list[position].percent,
                    "maxBerry" to list[position].maxBerry,
                    "d_day" to list[position].d_day
                    //"isDonateHistory" to isDonateHistory
                )
            }
        }
    }

    inner class Holder(itemView : View): RecyclerView.ViewHolder(itemView){
        var thumbnail = itemView.findViewById(R.id.li_donate_img_url) as ImageView
        var container = itemView.findViewById(R.id.li_donate_container) as RelativeLayout
        var d_day = itemView.findViewById(R.id.li_donate_d_day) as TextView
        var title = itemView.findViewById(R.id.li_donate_title) as TextView
        var association = itemView.findViewById(R.id.li_donate_association) as TextView
        var percent = itemView.findViewById(R.id.li_donate_percent) as TextView
        var berry_num = itemView.findViewById(R.id.li_donate_berry_num) as TextView
        var progress = itemView.findViewById(R.id.li_donate_progress) as ProgressBar
    }
}