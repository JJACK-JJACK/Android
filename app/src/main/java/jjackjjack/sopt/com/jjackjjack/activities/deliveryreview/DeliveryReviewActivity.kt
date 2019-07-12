package jjackjjack.sopt.com.jjackjjack.activities.deliveryreview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.webkit.URLUtil
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kmj.imageslider.DeliveryReviewImageAdapter
import jjackjjack.sopt.com.jjackjjack.*
import jjackjjack.sopt.com.jjackjjack.activities.home.MainActivity
import jjackjjack.sopt.com.jjackjjack.activities.berrycharge.BerryChargeActivity
import jjackjjack.sopt.com.jjackjjack.activities.berryusehistory.BerryHistoryActivity
import jjackjjack.sopt.com.jjackjjack.activities.donate.DonateActivity
import jjackjjack.sopt.com.jjackjjack.activities.donateparicipation.DonateParticipationActivity
import jjackjjack.sopt.com.jjackjjack.activities.mypage.MyPageActivity
import jjackjjack.sopt.com.jjackjjack.model.DeliveryReviewImageInfo
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController
import jjackjjack.sopt.com.jjackjjack.interfaces.onDrawer
import jjackjjack.sopt.com.jjackjjack.model.TotalDonateInfo
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import jjackjjack.sopt.com.jjackjjack.network.data.DonatedDetailedData
import jjackjjack.sopt.com.jjackjjack.network.response.get.GetDonateParticipationDetailResponse
import jjackjjack.sopt.com.jjackjjack.network.response.get.GetmyBerryResponse
import jjackjjack.sopt.com.jjackjjack.network.response.get.GettotalDonateResponse
import jjackjjack.sopt.com.jjackjjack.utillity.Constants
import jjackjjack.sopt.com.jjackjjack.utillity.Secret
import jjackjjack.sopt.com.jjackjjack.utillity.Secret.Companion.NETWORK_LIST_SUCCESS
import kotlinx.android.synthetic.main.activity_ranking.*
import kotlinx.android.synthetic.main.content_activity_ranking.*
import kotlinx.android.synthetic.main.nav_drawer.*
import kotlinx.android.synthetic.main.toolbar_with_hamburger.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat


class DeliveryReviewActivity : AppCompatActivity(), onDrawer {

    lateinit var mAdapter: DeliveryReviewImageAdapter

    lateinit var btnFset: Array<ImageView>

    lateinit var btnAset: Array<View>

    lateinit var actSet: Array<Class<out AppCompatActivity>>

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    val dataList: ArrayList<DeliveryReviewImageInfo> by lazy {
        ArrayList<DeliveryReviewImageInfo>()
    }
    val dataList_img: ArrayList<DeliveryReviewImageInfo> by lazy {
        ArrayList<DeliveryReviewImageInfo>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)
        initialUI()


        mRecyclerView.adapter = mAdapter
        mRecyclerView.setOnClickListener {
            startActivity<BerryReviewActivity>()
        }
    }

    override fun onResume() {
        super.onResume()
        gettotalDonateResponse()
        getDonateImageResponse()
    }
    private fun initialUI() {
        btn_home.setOnClickListener {
            startActivity<MainActivity>()
            finish()
        }
        drawerUI()

        mAdapter = DeliveryReviewImageAdapter(this, dataList)

        val lm = LinearLayoutManager(this)
        mRecyclerView.layoutManager = lm
        lm.setOrientation(LinearLayoutManager.HORIZONTAL)
        mRecyclerView.setHasFixedSize(true)
    }

    override fun drawerUI() {
        actSet = arrayOf(
            MainActivity::class.java, DonateParticipationActivity::class.java,
            DeliveryReviewActivity::class.java, MyPageActivity::class.java,
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
            getmyBerryResponse()
        }

        btn_cancel.setOnClickListener {
            if (ly_drawer.isDrawerOpen(Gravity.END)) {
                ly_drawer.closeDrawer(Gravity.END)
            }
        }

        tv_drawer_nickname.text = SharedPreferenceController.getUserNickname(this)//닉네임 DB 저장한 거 가져오는거
        tv_drawer_email.text = SharedPreferenceController.getUserEmail(this) // 이메일 DB 저장한 거
        if(URLUtil.isValidUrl(SharedPreferenceController.getUserImg(this))){
            Glide.with(this).load(SharedPreferenceController.getUserImg(this))
                .apply(RequestOptions.circleCropTransform())?.into(iv_drawer_profileimg)
        } //이미지 DB에서 가져오기 나중에 없을때 default 이미지 뜨게 처리해야함

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
                                dataList_img.add(
                                    DeliveryReviewImageInfo(
                                        receiveData[i].thumbnail
                                    )
                                )
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

    private fun updateDonateList(list: ArrayList<DeliveryReviewImageInfo>) {
        dataList.clear()
        dataList.addAll(list)
        mAdapter.notifyDataSetChanged()
    }

    private fun gettotalDonateResponse(){
        val gettotalDonateResponse =
                networkService.gettotalDonateResponse()

        gettotalDonateResponse.enqueue(object : Callback<GettotalDonateResponse>{
            override fun onFailure(call: Call<GettotalDonateResponse>, t: Throwable) {
                Log.d("Response error","조회실패")
            }

            override fun onResponse(call: Call<GettotalDonateResponse>, response: Response<GettotalDonateResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == NETWORK_LIST_SUCCESS){
                        val receiveData : ArrayList<TotalDonateInfo> = response.body()!!.data

                        val dec = DecimalFormat("#,000")
                        val total_berry = dec.format(receiveData[0].totalDonate)

                        rank_totalDonate.text = total_berry.toString()
                    }
                }
            }
        })
    }
    private fun getmyBerryResponse(){
        var token:String = SharedPreferenceController.getAuthorization(this)

        val getmyBerryResponse =
            networkService.getmyBerryResponse("application/json",token)

        getmyBerryResponse.enqueue(object : Callback<GetmyBerryResponse> {
            override fun onFailure(call: Call<GetmyBerryResponse>, t: Throwable) {
                Log.d("No berry", "No Berry")
            }

            override fun onResponse(call: Call<GetmyBerryResponse>, response: Response<GetmyBerryResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == Secret.NETWORK_LIST_SUCCESS){
                        val receiveData = response.body()?.data

                        val dec = DecimalFormat("#,000")
                        val dec_berry : String

                        if(receiveData.toString().length <= 3){
                            dec_berry = receiveData.toString()
                        }else{
                            dec_berry = dec.format(receiveData)
                        }

                        drawer_myberry.text = dec_berry
                    }
                }
            }
        })
    }
}