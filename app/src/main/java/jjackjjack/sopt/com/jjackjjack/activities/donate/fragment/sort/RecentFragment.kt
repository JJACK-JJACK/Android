package jjackjjack.sopt.com.jjackjjack.activities.donate.fragment.sort

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jjackjjack.sopt.com.jjackjjack.list.DonateListRecyclerViewAdapter
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.model.DonateInfo
import kotlinx.android.synthetic.main.fragment_recent_category.*

class RecentFragment : Fragment(){

    lateinit var donateListRecyclerViewAdapter: DonateListRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_recent_category, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var list: ArrayList<DonateInfo> = ArrayList()

//        list.add(
//            DonateInfo(
//                "64", "혜리에게 따듯한 이불을", "솝트", "35", "150.000"
//            )
//        )
//        list.add(
//            DonateInfo(
//                "15", "윤희에게 따듯한 이불을", "솝트", "61", "199.999"
//            )
//        )
//        list.add(
//            DonateInfo(
//                "33", "민진에게 따듯한 이불을", "솝트", "88", "130.000"
//            )
//        )
//        list.add(
//            DonateInfo(
//                "20", "상일에게 따듯한 이불을", "솝트", "10", "222.000"
//            )
//        )
        donateListRecyclerViewAdapter =
            DonateListRecyclerViewAdapter(context!!, list, false)
        rv_recent_category.adapter = donateListRecyclerViewAdapter
        rv_recent_category.layoutManager = LinearLayoutManager(context!!)
    }

}