package jjackjjack.sopt.com.jjackjjack.activities.berryuse

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import jjackjjack.sopt.com.jjackjjack.R

class BerryHistoryAdapter(val context: Context, val berryhistoryItem: ArrayList<BerryHistoryItem>) : RecyclerView.Adapter<BerryHistoryAdapter.Holder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.li_useberry, p0, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(berryhistoryItem[position], context)

        if(berryhistoryItem[position].li_berryhistory_group == ""){
            holder.li_berryhistory_berry!!.setTextColor(Color.parseColor("#ffc21f"))
        }
        else{
            holder.li_berryhistory_berry!!.setTextColor(Color.parseColor("#da4830"))
        }
    }
/* (1) Adapter의 파라미터에 람다식 itemClick을 넣는다. */


    override fun getItemCount(): Int {
        return berryhistoryItem.size
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        /* (2) Holder에서 클릭에 대한 처리를 할 것이므로, Holder의 파라미터에 람다식 itemClick을 넣는다. */
        val li_berryhistory_title = itemView?.findViewById<TextView>(R.id.li_berryhistory_title)
        val li_berryhistory_time = itemView?.findViewById<TextView>(R.id.li_berryhistory_time)
        val li_berryhistory_berry = itemView?.findViewById<TextView>(R.id.li_berryhistory_berry)
        val li_berryhistory_group = itemView?.findViewById<TextView>(R.id.li_berryhistory_group)


        fun bind(berryhistoryItem: BerryHistoryItem, context: Context) {
            li_berryhistory_title?.text = berryhistoryItem.li_berryhistory_title
            li_berryhistory_time?.text = berryhistoryItem.li_berryhistory_time
            li_berryhistory_berry?.text = berryhistoryItem.li_berryhistory_berry
            li_berryhistory_group?.text = berryhistoryItem.li_berryhistory_group

        }
    }
}