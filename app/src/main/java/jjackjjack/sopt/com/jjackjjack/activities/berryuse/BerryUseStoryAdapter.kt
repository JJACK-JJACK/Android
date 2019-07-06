package jjackjjack.sopt.com.jjackjjack.activities.berryuse

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import jjackjjack.sopt.com.jjackjjack.R

class BerryUseStoryAdapter (val context: Context, val berryusestoryitem: ArrayList<BerryUseStoryItem>, val itemClick:(BerryUseStoryItem)->Unit):
    RecyclerView.Adapter<BerryUseStoryAdapter.Holder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_use_story,p0,false)
        return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return berryusestoryitem.size
    }

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        p0?.bind(berryusestoryitem[p1], context)
    }

    inner class Holder(itemView: View?, itemClick:(BerryUseStoryItem)->Unit):RecyclerView.ViewHolder(itemView!!){
        val use_story_title = itemView?.findViewById<TextView>(R.id.use_story_title)
        val use_story_content = itemView?.findViewById<TextView>(R.id.use_story_content)

        fun bind(berryUseStoryItem: BerryUseStoryItem, context: Context){
//            if(rankingItem.photo != ""){
//                //서버?
//                val resourceId = context3.resources.getIdentifier(rankingItem.photo, "drawable",context3.packageName)
//                rank_userimg?.setImageResource(resourceId)
//            }else{
//                rank_userimg?.setImageResource(R.drawable.abc_text_cursor_material)
//            }
            use_story_title?.text = berryUseStoryItem.use_story_title
            use_story_content?.text = berryUseStoryItem.use_story_content
        }
    }


}