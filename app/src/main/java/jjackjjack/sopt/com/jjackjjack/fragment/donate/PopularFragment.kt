package jjackjjack.sopt.com.jjackjjack.fragment.donate

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jjackjjack.sopt.com.jjackjjack.DonateListRecyclerViewAdapter
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.data.DonateInfoData
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

        var dataList: ArrayList<DonateInfoData> = ArrayList()

        dataList.add(
            DonateInfoData(
                "64", "병제에게 맛있는 한끼를", "솝트", "99", "150.000"
            )
        )
        dataList.add(
            DonateInfoData(
                "15", "동진에게 맛있는 한끼를", "솝트", "55", "199.999"
            )
        )
        dataList.add(
            DonateInfoData(
                "33", "연수에게 맛있는 한끼를", "솝트", "20", "130.000"
            )
        )


        donateListRecyclerViewAdapter = DonateListRecyclerViewAdapter(context!!, dataList, false)
        rv_popular_category.adapter = donateListRecyclerViewAdapter
        rv_popular_category.layoutManager = LinearLayoutManager(context!!)
    }
}