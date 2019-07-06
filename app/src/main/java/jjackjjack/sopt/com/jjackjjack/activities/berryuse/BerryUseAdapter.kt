package jjackjjack.sopt.com.jjackjjack.activities.berryuse

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.kmj.imageslider.RankBtnItem
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.activities.berryuse.BerryUseItem

class BerryUseAdapter(val context: Context, val berryuseItem: ArrayList<BerryUseItem>) : RecyclerView.Adapter<BerryUseAdapter.Holder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.li_donate_use_plan, p0, false)
        return Holder(view)
    }


    override fun onBindViewHolder(p0: Holder, p1: Int) {
        p0?.bind(berryuseItem[p1], context)
    }
/* (1) Adapter의 파라미터에 람다식 itemClick을 넣는다. */


    override fun getItemCount(): Int {
        return berryuseItem.size
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        /* (2) Holder에서 클릭에 대한 처리를 할 것이므로, Holder의 파라미터에 람다식 itemClick을 넣는다. */
        val li_donate_use_plan_idx_num = itemView?.findViewById<TextView>(R.id.li_donate_use_plan_idx_num)
        val li_donate_use_plan_detail = itemView?.findViewById<TextView>(R.id.li_donate_use_plan_detail)
        val li_donate_use_plan_berry_num = itemView?.findViewById<TextView>(R.id.li_donate_use_plan_berry_num)


        fun bind(berryuseItem: BerryUseItem, context: Context) {
//            if (rankImgItem.rank_userimg != "") {
//                val resourceId = context.resources.getIdentifier(rankImgItem.rank_userimg, "drawable", context.packageName)
//                dogPhoto?.setImageResource(resourceId)
//            } else {
//                dogPhoto?.setImageResource(R.mipmap.ic_launcher)
//            }

            li_donate_use_plan_idx_num?.text = berryuseItem.li_donate_use_plan_idx_num
            li_donate_use_plan_detail?.text = berryuseItem.li_donate_use_plan_detail
            li_donate_use_plan_berry_num?.text = berryuseItem.li_donate_use_plan_berry_num
        }

//    inner class Holder(itemView: View?, itemClick: (RankBtnItem)->Unit):RecyclerView.ViewHolder(itemView!!){
//        val rankBtn = itemView?.findViewById<TextView>(R.id.rankBtn)
//
//        fun bind(rankBtnItem: RankBtnItem, context: Context){
//            if(rankBtnItem.btnText !=""){
//                val resourceId = context.resources.getIdentifier(rankBtnItem.btnText, "drawable", context.packageName)
//                /*if (rankBtn != null) {
//                    rankBtn.setText(resourceId)
//                }*/
//                //클릭됐을 때 처리할 일을 itemclick으로 설정,
//                //(RankBtnItem)->Unit에 대한 함수는 나중에 rankactivity에서 작성
//                itemView.setOnClickListener{itemClick(rankBtnItem)}
//            }
//            rankBtn?.text = rankBtnItem.btnText
//        }
//    }

    }
}