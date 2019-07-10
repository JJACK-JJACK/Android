package jjackjjack.sopt.com.jjackjjack.activities.rank

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.example.kmj.imageslider.RankImgAdapter
import jjackjjack.sopt.com.jjackjjack.*
import jjackjjack.sopt.com.jjackjjack.activities.MainActivity
import jjackjjack.sopt.com.jjackjjack.activities.berrycharge.BerryChargeActivity
import jjackjjack.sopt.com.jjackjjack.activities.berryreview.BerryreviewActivity
import jjackjjack.sopt.com.jjackjjack.activities.berryuse.BerryHistoryActivity
import jjackjjack.sopt.com.jjackjjack.activities.donate.DonateActivity
import jjackjjack.sopt.com.jjackjjack.activities.donaterecord.DonateRecordActivity
import jjackjjack.sopt.com.jjackjjack.activities.mypage.MyPageActivity
import jjackjjack.sopt.com.jjackjjack.interfaces.onDrawer
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import jjackjjack.sopt.com.jjackjjack.network.data.DonatedDetailedData
import jjackjjack.sopt.com.jjackjjack.network.response.get.GetDonateParticipationDetailResponse
import jjackjjack.sopt.com.jjackjjack.utillity.Constants
import kotlinx.android.synthetic.main.activity_ranking.*
import kotlinx.android.synthetic.main.content_activity_ranking.*
import kotlinx.android.synthetic.main.nav_drawer.*
import kotlinx.android.synthetic.main.toolbar_with_hamburger.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RankActivity : AppCompatActivity(), onDrawer {

    lateinit var mAdapter: RankImgAdapter

    lateinit var btnFset: Array<ImageView>

    lateinit var btnAset: Array<View>

    lateinit var actSet: Array<Class<out AppCompatActivity>>

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    val dataList: ArrayList<RankImgItem> by lazy {
        ArrayList<RankImgItem>()
    }
    val dataList_img: ArrayList<RankImgItem> by lazy {
        ArrayList<RankImgItem>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)
        initialUI()
        //이거 나중에 함수로 빼주기!

        getDonateImageResponse()
        mRecyclerView.adapter = mAdapter
        mRecyclerView.setOnClickListener {
            startActivity<BerryreviewActivity>()
        }
    }

    private fun initialUI() {
        btn_home.setOnClickListener {
            startActivity<MainActivity>()
            finish()
        }
        drawerUI()

        mAdapter = RankImgAdapter(this, dataList)

        val lm = LinearLayoutManager(this)
        mRecyclerView.layoutManager = lm
        lm.setOrientation(LinearLayoutManager.HORIZONTAL)
        mRecyclerView.setHasFixedSize(true)
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
        drawerBtnSetting(Constants.ACTIVITY_RANK)
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

    private fun getDonateImageResponse() {
        val getDonateImageResponse =
            networkService.getDeliveryReviewResponse()

        getDonateImageResponse.enqueue(object : Callback<GetDonateParticipationDetailResponse> {
            override fun onFailure(call: Call<GetDonateParticipationDetailResponse>, t: Throwable) {
                Log.d("hello", t.toString())
            }

            override fun onResponse(
                call: Call<GetDonateParticipationDetailResponse>,
                response: Response<GetDonateParticipationDetailResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 201) {
                        val receiveData: ArrayList<DonatedDetailedData> = response.body()!!.data
                        if (receiveData.size > 0) {
                            for (i in 0 until receiveData.size) {
                                dataList_img.add(RankImgItem(receiveData[i].thumbnail))
                            }
                        }
                        updateDonateList(dataList_img)
                    }
                } else if (response.body()!!.status == 600) {
                    toast(response.body()!!.message)
                }
            }
        })
    }

    private fun updateDonateList(list: ArrayList<RankImgItem>) {
        dataList.clear()
        dataList.addAll(list)
        mAdapter.notifyDataSetChanged()
    }
}