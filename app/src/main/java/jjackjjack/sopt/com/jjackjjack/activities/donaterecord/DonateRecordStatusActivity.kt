package jjackjjack.sopt.com.jjackjjack.activities.donaterecord

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.data.DonateUsePlanData
import jjackjjack.sopt.com.jjackjjack.list.DonateUsePlanRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_donate_record_status.*
import kotlinx.android.synthetic.main.fragment_berryuse_review.*
import kotlinx.android.synthetic.main.fragment_use_berry.*
import kotlinx.android.synthetic.main.header_img.*
import kotlinx.android.synthetic.main.li_state.*

class DonateRecordStatusActivity : AppCompatActivity() {

    lateinit var donateUsePlanRecyclerViewAdapter: DonateUsePlanRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate_record_status)
        initialUI()
    }

    private fun initialUI() {
        btn_toolbar_back.setOnClickListener {
            finish()
        }

        li_state_berry_num.text = intent.getStringExtra("berry_num")
        li_state_d_day.text = intent.getStringExtra("d_day")
        li_state_percent.text = intent.getStringExtra("percent")
        li_state_progress.progress = intent.getStringExtra("percent").toInt()
        donate_detailed_title.text = intent.getStringExtra("title")
        donate_detailed_association.text = intent.getStringExtra("association")

        var dataList: ArrayList<DonateUsePlanData> = ArrayList()

        dataList.add(
            DonateUsePlanData(
                "1", "입양지원 활동 및 입양진행", "30.000"
            )
        )
        dataList.add(
            DonateUsePlanData(
                "2", "위급한 유기견 대상 영양제 지원, 예방접종 진행", "19.000"
            )
        )
        dataList.add(
            DonateUsePlanData(
                "3", "약품 및 물품비용", "23.000"
            )
        )
        dataList.add(
            DonateUsePlanData(
                "4", "족발 대자 3개", "120.000"
            )
        )
        donateUsePlanRecyclerViewAdapter = DonateUsePlanRecyclerViewAdapter(this, dataList)
        rv_donate_use_container.adapter = donateUsePlanRecyclerViewAdapter
        rv_donate_use_container.layoutManager = LinearLayoutManager(this)

    }
}
