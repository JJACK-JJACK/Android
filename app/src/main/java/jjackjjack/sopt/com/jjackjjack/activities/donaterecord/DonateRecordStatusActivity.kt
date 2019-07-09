package jjackjjack.sopt.com.jjackjjack.activities.donaterecord

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController
import jjackjjack.sopt.com.jjackjjack.model.DonateUsePlan
import jjackjjack.sopt.com.jjackjjack.list.DonateUsePlanRecyclerViewAdapter
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import jjackjjack.sopt.com.jjackjjack.network.data.DonatedDetailedData
import jjackjjack.sopt.com.jjackjjack.network.data.UsePlanData
import jjackjjack.sopt.com.jjackjjack.network.response.get.GetDonateParticipationDetailResponse
import jjackjjack.sopt.com.jjackjjack.utillity.Secret
import kotlinx.android.synthetic.main.activity_donate_record_status.*
import kotlinx.android.synthetic.main.donate_status.*
import kotlinx.android.synthetic.main.fragment_participation_status.*
import kotlinx.android.synthetic.main.fragment_use_berry.*
import kotlinx.android.synthetic.main.header_img.*
import kotlinx.android.synthetic.main.li_state.*
import org.jetbrains.anko.backgroundColor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Integer.parseInt

class DonateRecordStatusActivity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    val dataList: ArrayList<DonateUsePlan> by lazy {
        ArrayList<DonateUsePlan>()
    }

    val dataList_DonateRecordStatus : ArrayList<DonateUsePlan> by lazy {
        ArrayList<DonateUsePlan>()
    }

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

        li_state_berry_num.text = intent.getStringExtra("maxBerry")
        li_state_d_day.text = intent.getStringExtra("d_day")
        li_state_percent.text = intent.getStringExtra("percent")
        li_state_progress.progress = intent.getStringExtra("percent").toInt()
        donate_detailed_title.text = intent.getStringExtra("title")
        donate_detailed_association.text = intent.getStringExtra("centerName")

        var list: ArrayList<DonateUsePlan> = ArrayList()

        //list.add(
        //    DonateUsePlan(
        //        "1", "입양지원 활동 및 입양진행", "30.000"
        //    )
        //)
        //list.add(
        //    DonateUsePlan(
        //        "2", "위급한 유기견 대상 영양제 지원, 예방접종 진행", "19.000"
        //    )
        //)
        //list.add(
        //    DonateUsePlan(
        //       "3", "약품 및 물품비용", "23.000"
        //    )
        //)
        //list.add(
        //    DonateUsePlan(
        //        "4", "족발 대자 3개", "120.000"
        //    )
        //)

        initialUI_status()
        getDonateParticipateDetailResponse()
    }

    private fun getDonateParticipateDetailResponse() {
        val programId: String = "5d21f1ca41cb4a7984d17911"
        val token = SharedPreferenceController.getAuthorization(this)
        val getDonateParticipationDetailResponse =
            networkService.getDonateParticipationDetailResponse("application/json", token, programId)

        getDonateParticipationDetailResponse.enqueue(object : Callback<GetDonateParticipationDetailResponse> {
            override fun onFailure(call: Call<GetDonateParticipationDetailResponse>, t: Throwable) {
                Log.e("DB error", t.toString())
            }

            override fun onResponse(
                call: Call<GetDonateParticipationDetailResponse>,
                response: Response<GetDonateParticipationDetailResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == Secret.NETWORK_SUCCESS) {
                        val receiveData: ArrayList<DonatedDetailedData> = response.body()!!.data

                        if (receiveData.size > 0) {
                            if (receiveData[1].state == 0) {
                                status_ing.backgroundColor = Color.parseColor("red")
                                tv_participation_ing.setTextColor(Color.parseColor("red"))
                                participation_status_ing.backgroundColor = Color.parseColor("darkGrayA")

                            } else if (receiveData[1].state == 1) {
                                status_end.backgroundColor = Color.parseColor("green")
                                tv_participation_end.setTextColor(Color.parseColor("green"))
                                participation_status_end.backgroundColor = Color.parseColor("darkGrayA")
                            } else if (receiveData[1].state == 2) {
                                status_end.backgroundColor = Color.parseColor("blue")
                                tv_participation_finish.setTextColor(Color.parseColor("blue"))
                                participation_status_finish.backgroundColor = Color.parseColor("darkGrayA")
                            }
                            for(i in 0 until receiveData.size){
                                dataList_DonateRecordStatus[i]
                            }


                            //updateDonateRecordStatus()
                        }
                    } else {

                    }
                }
            }
        })
    }

    override fun onResume(){
        super.onResume()

        getDonateParticipateDetailResponse()
    }

    private fun updateDonateRecordStatus(list: ArrayList<DonateUsePlan>) {
        dataList.clear()
        dataList.addAll(list)
        donateUsePlanRecyclerViewAdapter.notifyDataSetChanged()
    }

    private fun initialUI_status() {
        donateUsePlanRecyclerViewAdapter =
            DonateUsePlanRecyclerViewAdapter(this, dataList)
        rv_donate_use_container.adapter = donateUsePlanRecyclerViewAdapter
        rv_donate_use_container.layoutManager = LinearLayoutManager(this)
    }
}