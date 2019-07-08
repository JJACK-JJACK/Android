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
import kotlinx.android.synthetic.main.fragment_popular_category.*

class PopularFragment : Fragment(){

    lateinit var donateListRecyclerViewAdapter: DonateListRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular_category, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var list: ArrayList<DonateInfo> = ArrayList()

//        list.add(
//            DonateInfo(
//                "64", "병제에게 맛있는 한끼를", "솝트", "99", "150.000"
//            )
//        )
//        list.add(
//            DonateInfo(
//                "15", "동진에게 맛있는 한끼를", "솝트", "55", "199.999"
//            )
//        )
//        list.add(
//            DonateInfo(
//                "33", "연수에게 맛있는 한끼를", "솝트", "20", "130.000"
//            )
//        )


        donateListRecyclerViewAdapter =
            DonateListRecyclerViewAdapter(context!!, list, false)
        rv_popular_category.adapter = donateListRecyclerViewAdapter
        rv_popular_category.layoutManager = LinearLayoutManager(context!!)
    }
}