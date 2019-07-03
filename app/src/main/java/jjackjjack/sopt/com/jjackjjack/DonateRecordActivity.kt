package jjackjjack.sopt.com.jjackjjack

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import jjackjjack.sopt.com.jjackjjack.data.DonateInfoData
import kotlinx.android.synthetic.main.activity_donate_record.*

class DonateRecordActivity : AppCompatActivity() {

    lateinit var donateListRecyclerViewAdapter: DonateListRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate_record)

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

        donateListRecyclerViewAdapter = DonateListRecyclerViewAdapter(this, dataList, true)
        rv_donate_record.adapter = donateListRecyclerViewAdapter
        rv_donate_record.layoutManager = LinearLayoutManager(this)
    }


}
