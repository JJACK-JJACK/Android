package jjackjjack.sopt.com.jjackjjack.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.model.DonateUsePlan

class DonateUsePlanRecyclerViewAdapter (val ctx: Context, var list: ArrayList<DonateUsePlan>): RecyclerView.Adapter<DonateUsePlanRecyclerViewAdapter.Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.li_donate_use_plan, viewGroup, false )
        return Holder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.idx.text = " " + list[position].idx
        holder.plan_detail.text = list[position].plan_detail
        holder.berry_num.text = list[position].berry_num
    }

    inner class Holder(itemView : View): RecyclerView.ViewHolder(itemView){
        var idx = itemView.findViewById(R.id.li_donate_use_plan_idx_num) as TextView
        var plan_detail = itemView.findViewById(R.id.li_donate_use_plan_detail) as TextView
        var berry_num = itemView.findViewById(R.id.li_donate_use_plan_berry_num) as TextView
    }
}