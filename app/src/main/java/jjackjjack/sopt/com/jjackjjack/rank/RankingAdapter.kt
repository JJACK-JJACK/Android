package jjackjjack.sopt.com.jjackjjack.rank

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import jjackjjack.sopt.com.jjackjjack.R
import java.util.*

class RankingAdapter (val context3:Context, val rankingItemList: ArrayList<RankingItem>, val itemClick:(RankingItem)->Unit):RecyclerView.Adapter<RankingAdapter.Holder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RankingAdapter.Holder {
        val view = LayoutInflater.from(context3).inflate(R.layout.li_ranking,p0,false)
        return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return rankingItemList.size
    }

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        p0?.bind(rankingItemList[p1], context3)
    }

    inner class Holder(itemView: View?, itemClick:(RankingItem)->Unit):RecyclerView.ViewHolder(itemView!!){
        val rank_ranking = itemView?.findViewById<TextView>(R.id.rank_ranking)
        val rank_userimg = itemView?.findViewById<ImageView>(R.id.rank_userimg)
        val rank_username = itemView?.findViewById<TextView>(R.id.rank_username)
        val rank_berry = itemView?.findViewById<TextView>(R.id.rank_berry)
        val rank_times = itemView?.findViewById<TextView>(R.id.rank_times)

        fun bind(rankingItem: RankingItem, context3: Context){
//            if(rankingItem.photo != ""){
//                //서버?
//                val resourceId = context3.resources.getIdentifier(rankingItem.photo, "drawable",context3.packageName)
//                rank_userimg?.setImageResource(resourceId)
//            }else{
//                rank_userimg?.setImageResource(R.drawable.abc_text_cursor_material)
//            }
            rank_ranking?.text = rankingItem.rank_ranking
            rank_username?.text= rankingItem.rank_username
            rank_berry?.text = rankingItem.rank_berry
            rank_times?.text = rankingItem.rank_times
        }
    }


}