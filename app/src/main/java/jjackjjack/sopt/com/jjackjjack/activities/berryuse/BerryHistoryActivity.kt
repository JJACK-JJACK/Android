package jjackjjack.sopt.com.jjackjjack.activities.berryuse

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import jjackjjack.sopt.com.jjackjjack.R
import kotlinx.android.synthetic.main.activity_mypage_berryhistory.*
import kotlinx.android.synthetic.main.fragment_berryuse_review.*

class BerryHistoryActivity : AppCompatActivity() {
    var BerryHistoryList = arrayListOf<BerryHistoryItem>(
        BerryHistoryItem("베리충전", "2019.05.30 10:59","+128","장애인재단"),
        BerryHistoryItem("베리충전", "2019.05.30 10:59","+128","장애인재단"),
        BerryHistoryItem("베리충전", "2019.05.30 10:59","+11238","장애인재단"),
        BerryHistoryItem("베리충전", "2019.05.30 10:59","+128","장애인재단"),
        BerryHistoryItem("베리충전", "2019.05.30 10:59","+128","장애인재단"),
        BerryHistoryItem("베리충전", "2019.05.30 10:59","+128","장애인재단")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_berryhistory)

        val berryhistoryAdapter = BerryHistoryAdapter(this, BerryHistoryList)

        rv_berryhistory.adapter = berryhistoryAdapter

        val lm = LinearLayoutManager(this)
        rv_berryhistory.layoutManager = lm
        lm.setOrientation(LinearLayoutManager.VERTICAL)
        rv_berryhistory.setHasFixedSize(true)

        btn_toolbar_back.setOnClickListener {
            finish()
        }
    }
}
