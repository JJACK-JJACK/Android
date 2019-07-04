package jjackjjack.sopt.com.jjackjjack.activities.donate.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jjackjjack.sopt.com.jjackjjack.list.DonateListRecyclerViewAdapter
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.data.DonateInfoData
import kotlinx.android.synthetic.main.fragment_unpopular_category.*

class UnpopularFragment : Fragment(){

    lateinit var donateListRecyclerViewAdapter: DonateListRecyclerViewAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_unpopular_category, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var dataList: ArrayList<DonateInfoData> = ArrayList()

        dataList.add(
            DonateInfoData(
                "64", "민정에게 편안한 보금자리를", "솝트", "99", "150.000"
            )
        )
        dataList.add(
            DonateInfoData(
                "15", "찬은에게 편안한 보금자리를", "솝트", "55", "199.999"
            )
        )
        dataList.add(
            DonateInfoData(
                "33", "정환에게 편안한 보금자리를", "솝트", "20", "130.000"
            )
        )
        dataList.add(
            DonateInfoData(
                "2", "하영에게 편안한 보금자리를", "솝트", "20", "655.222"
            )
        )
        dataList.add(
            DonateInfoData(
                "100", "진희에게 편안한 보금자리를", "솝트", "1", "130.000"
            )
        )


        donateListRecyclerViewAdapter =
            DonateListRecyclerViewAdapter(context!!, dataList, false)
        rv_unpopular_category.adapter = donateListRecyclerViewAdapter
        rv_unpopular_category.layoutManager = LinearLayoutManager(context!!)
    }


}