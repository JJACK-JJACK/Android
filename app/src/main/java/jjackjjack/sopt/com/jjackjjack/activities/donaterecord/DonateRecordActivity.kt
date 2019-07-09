package jjackjjack.sopt.com.jjackjjack.activities.donaterecord

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import jjackjjack.sopt.com.jjackjjack.activities.MainActivity
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.activities.berrycharge.BerryChargeActivity
import jjackjjack.sopt.com.jjackjjack.activities.berryuse.BerryHistoryActivity
import jjackjjack.sopt.com.jjackjjack.activities.donate.DonateActivity
import jjackjjack.sopt.com.jjackjjack.list.DonateListRecyclerViewAdapter
import jjackjjack.sopt.com.jjackjjack.activities.mypage.MyPageActivity
import jjackjjack.sopt.com.jjackjjack.activities.rank.RankActivity
import jjackjjack.sopt.com.jjackjjack.data.DonateInfoData
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController
import jjackjjack.sopt.com.jjackjjack.interfaces.onDrawer
import jjackjjack.sopt.com.jjackjjack.list.DonateParticipateListRecyclerViewAdapter
import jjackjjack.sopt.com.jjackjjack.model.DonateInfo
import jjackjjack.sopt.com.jjackjjack.model.DonateParticipationInfo
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import jjackjjack.sopt.com.jjackjjack.network.data.DonateRecordData
import jjackjjack.sopt.com.jjackjjack.network.data.DonatedDetailedData
import jjackjjack.sopt.com.jjackjjack.network.response.get.GetDonateParticipationResponse
import jjackjjack.sopt.com.jjackjjack.network.response.get.GetDonateRecordResponse
import jjackjjack.sopt.com.jjackjjack.utillity.Constants
import jjackjjack.sopt.com.jjackjjack.utillity.Secret
import kotlinx.android.synthetic.main.activity_donate_record.ly_drawer
import kotlinx.android.synthetic.main.content_activity_donate_record.*
import kotlinx.android.synthetic.main.nav_drawer.*
import kotlinx.android.synthetic.main.toolbar_with_hamburger.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DonateRecordActivity : AppCompatActivity(), onDrawer {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    val dataList : ArrayList<DonateInfo> by lazy{
        ArrayList<DonateInfo>()
    }

    lateinit var btnFset: Array<ImageView>

    lateinit var btnAset: Array<View>

    lateinit var actSet: Array<Class<out AppCompatActivity>>

    lateinit var donateParticipateListRecyclerViewAdapter: DonateParticipateListRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate_record)
        initialUI()

        getDonateRecordResponse()
        getDonateParticipationResponse()
    }

    private fun initialUI() {

        btn_home.setOnClickListener {
            startActivity<MainActivity>()
            finish()
        }
        drawerUI()

        var dataList: ArrayList<DonateParticipationInfo> = ArrayList()

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

        donateParticipateListRecyclerViewAdapter = DonateParticipateListRecyclerViewAdapter(this, dataList)
        rv_donate_record.adapter = donateParticipateListRecyclerViewAdapter
        rv_donate_record.layoutManager = LinearLayoutManager(this)
    }

    override fun drawerUI() {
        actSet = arrayOf(
            MainActivity::class.java, DonateRecordActivity::class.java,
            RankActivity::class.java, MyPageActivity::class.java,
            BerryChargeActivity::class.java, BerryHistoryActivity::class.java
        )

        btnFset = arrayOf( //프래그먼트로 가는 버튼
            btn_drawer_f_child, btn_drawer_f_senior, btn_drawer_f_animal, btn_drawer_f_disabled,
            btn_drawer_f_env, btn_drawer_f_emergency
        )

        btnAset = arrayOf(
            btn_drawer_home, btn_drawer_donate_record, btn_drawer_rank,
            btn_drawer_mypage, btn_drawer_berrycharge, btn_drawer_usehistory
        )

        drawerBtnSetting(Constants.ACTIVITY_DONATE_RECORD)
    }

    override fun drawerBtnSetting(activityType: Int) {
        btn_hambuger.setOnClickListener {
            if (!ly_drawer.isDrawerOpen(Gravity.END)) {
                ly_drawer.openDrawer(Gravity.END)
            }
        }

        btn_cancel.setOnClickListener {
            if (ly_drawer.isDrawerOpen(Gravity.END)) {
                ly_drawer.closeDrawer(Gravity.END)
            }
        }

        for (i in 0 until btnAset.size) {
            btnAset[i].setOnClickListener {
                val intent = Intent(this, actSet[i])
                ly_drawer.closeDrawer(Gravity.END)
                startActivity(intent)
                finish()
            }
        }

        for (i in 0 until btnFset.size) {
            btnFset[i].setOnClickListener {
                startActivity<DonateActivity>("fragment" to i)
                Handler().postDelayed({ ly_drawer.closeDrawer(Gravity.END) }, 110)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        if (ly_drawer.isDrawerOpen(Gravity.END)) {
            ly_drawer.closeDrawer(Gravity.END)
        } else {
            super.onBackPressed()
        }
    }

    private fun getDonateRecordResponse() {
        var token: String = SharedPreferenceController.getAuthorization(this)

        val getDonateRecordResponse =
            networkService.getDonateRecordResponse(token)

        getDonateRecordResponse.enqueue(object : Callback<GetDonateRecordResponse> {
            override fun onFailure(call: Call<GetDonateRecordResponse>, t: Throwable) {
                Log.d("hello", t.toString())
            }

            override fun onResponse(call: Call<GetDonateRecordResponse>, response: Response<GetDonateRecordResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == Secret.NETWORK_SUCCESS) {
                        val receiveData: DonateRecordData = response.body()!!.data
                        total_berry.text = receiveData.donateBerry.toString()
                        participation_num.text = receiveData.donate.toString()
                    }
                } else if (response.body()!!.status == 600) {
                    toast(response.body()!!.message)
                }
            }
        })
    }

    private fun getDonateParticipationResponse() {

        var token: String = SharedPreferenceController.getAuthorization(this)

        val getDonateParticipationResponse = networkService.getDonateParticipationResponse(token)
        getDonateParticipationResponse.enqueue(object : Callback<GetDonateParticipationResponse> {
            override fun onFailure(call: Call<GetDonateParticipationResponse>, t: Throwable) {
                Log.e("hello", t.toString())
            }

            override fun onResponse(
                call: Call<GetDonateParticipationResponse>,
                response: Response<GetDonateParticipationResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == Secret.NETWORK_SUCCESS) {
                        val receiveData: ArrayList<DonatedDetailedData>? = response.body()?.data
                        if (receiveData!!.size > 0) {
                            for (i in 0 until receiveData.size) {
                                dataList.add(
                                    DonateInfo(
                                        receiveData[i]._id,
                                        receiveData[i].thumbnail,
                                        0.toString(), //임시값
                                        receiveData[i].title,
                                        receiveData[i].centerName,
                                        receiveData[i].percentage.toString(),
                                        0.toString() //임시값
                                    )
                                )
                            }
                        }
                    } else if (response.body()!!.status == 600) {
                        toast(response.body()!!.message)
                    }
                }
            }
        })
    }
}