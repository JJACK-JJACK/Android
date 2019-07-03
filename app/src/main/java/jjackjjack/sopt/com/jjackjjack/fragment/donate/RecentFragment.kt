package jjackjjack.sopt.com.jjackjjack.fragment.donate

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jjackjjack.sopt.com.jjackjjack.DonateDetailedActivity
import jjackjjack.sopt.com.jjackjjack.DonateListRecyclerViewAdapter
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.data.DonateInfoData
import kotlinx.android.synthetic.main.fragment_recent_category.*

class RecentFragment : Fragment(){

    lateinit var donateListRecyclerViewAdapter: DonateListRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_recent_category, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var dataList: ArrayList<DonateInfoData> = ArrayList()

        dataList.add(
            DonateInfoData(
                "64", "혜리에게 따듯한 이불을", "솝트", "35", "150.000"
            )
        )
        dataList.add(
            DonateInfoData(
                "15", "윤희에게 따듯한 이불을", "솝트", "61", "199.999"
            )
        )
        dataList.add(
            DonateInfoData(
                "33", "민진에게 따듯한 이불을", "솝트", "88", "130.000"
            )
        )
        dataList.add(
            DonateInfoData(
                "20", "상일에게 따듯한 이불을", "솝트", "10", "222.000"
            )
        )
        donateListRecyclerViewAdapter = DonateListRecyclerViewAdapter(context!!, dataList)
        rv_recent_category.adapter = donateListRecyclerViewAdapter
        rv_recent_category.layoutManager = LinearLayoutManager(context!!)
    }

}