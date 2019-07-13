package jjackjjack.sopt.com.jjackjjack.activities.donateparicipation

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.webkit.URLUtil
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jjackjjack.sopt.com.jjackjjack.activities.home.MainActivity
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.activities.berrycharge.BerryChargeActivity
import jjackjjack.sopt.com.jjackjjack.activities.berryusehistory.BerryHistoryActivity
import jjackjjack.sopt.com.jjackjjack.activities.donate.DonateActivity
import jjackjjack.sopt.com.jjackjjack.activities.mypage.MyPageActivity
import jjackjjack.sopt.com.jjackjjack.activities.deliveryreview.DeliveryReviewActivity
import jjackjjack.sopt.com.jjackjjack.activities.stamp.StampActivity
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController
import jjackjjack.sopt.com.jjackjjack.interfaces.onDrawer
import jjackjjack.sopt.com.jjackjjack.list.DonateParticipationListRecyclerViewAdapter
import jjackjjack.sopt.com.jjackjjack.model.DonateParticipationInfo
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import jjackjjack.sopt.com.jjackjjack.network.data.DonateBerryData
import jjackjjack.sopt.com.jjackjjack.network.data.DonatedDetailedData
import jjackjjack.sopt.com.jjackjjack.network.response.get.GetDonateParticipationBerryNumResponse
import jjackjjack.sopt.com.jjackjjack.network.response.get.GetDonateParticipationResponse
import jjackjjack.sopt.com.jjackjjack.network.response.get.GetDonateRecordResponse
import jjackjjack.sopt.com.jjackjjack.network.response.get.GetmyBerryResponse
import jjackjjack.sopt.com.jjackjjack.utillity.ColorToast
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
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DonateParticipationActivity : AppCompatActivity(), onDrawer {

    private var mLastClickTime: Long = 0
    var amLastClickTime: Long = 0
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    val dataList: ArrayList<DonateParticipationInfo> by lazy {
        ArrayList<DonateParticipationInfo>()
    }
    val dataList_donateParticipateInfo: ArrayList<DonateParticipationInfo> by lazy {
        ArrayList<DonateParticipationInfo>()
    }

    lateinit var btnFset: Array<ImageView>

    lateinit var btnAset: Array<View>

    lateinit var actSet: Array<Class<out AppCompatActivity>>

    lateinit var donateParticipationListRecyclerViewAdapter: DonateParticipationListRecyclerViewAdapter

    val dec = DecimalFormat("#,000")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate_record)

        initialUI()
    }

    override fun onResume() { //수정부분
        super.onResume()
        dataList_donateParticipateInfo.clear()
        dataList.clear()
        getDonateRecordResponse()
        getDonateBerryNum()
        //getDonateParticipationResponse()
    }

    private fun initialUI() {

        btn_home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        donate_record_stamp.setOnClickListener {
            val intent = Intent(this, StampActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
        }
        drawerUI()

        donateParticipationListRecyclerViewAdapter = DonateParticipationListRecyclerViewAdapter(this, dataList)
        rv_donate_record.adapter = donateParticipationListRecyclerViewAdapter
        rv_donate_record.layoutManager = LinearLayoutManager(this)
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

        drawerBtnSetting(Constants.ACTIVITY_DONATE_RECORD)
    }

    override fun drawerBtnSetting(activityType: Int) {
        btn_hambuger.setOnClickListener {
            if (!ly_drawer.isDrawerOpen(Gravity.END)) {
                ly_drawer.openDrawer(Gravity.END)
            }
            getmyBerryResponse()
            tv_drawer_nickname.text = SharedPreferenceController.getUserNickname(this) //닉네임 DB 저장한 거 가져오는거
            tv_drawer_email.text = SharedPreferenceController.getUserEmail(this) // 이메일 DB 저장한 거
            if((SharedPreferenceController.getUserImg(this))!!.isNotEmpty()){
                Glide.with(this@DonateParticipationActivity)
                .load(SharedPreferenceController.getUserImg(this)).apply(RequestOptions.circleCropTransform())?.into(iv_drawer_profileimg)
            }else{
                Glide.with(this@DonateParticipationActivity)
                    .load(R.drawable.pofile)
                    .apply(RequestOptions.circleCropTransform())?.into(iv_drawer_profileimg)
            }
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
                if(SystemClock.elapsedRealtime()- amLastClickTime < 2000){
                    return@setOnClickListener
                }
                amLastClickTime = SystemClock.elapsedRealtime()
                val intent = Intent(this, actSet[i])
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                ly_drawer.closeDrawer(Gravity.END)
                Handler().postDelayed({startActivity(intent)}, 110)
                finish()
            }
        }

        for (i in 0 until btnFset.size) {
            btnFset[i].setOnClickListener {
                if(SystemClock.elapsedRealtime()-mLastClickTime < 2000){
                    return@setOnClickListener
                }
                mLastClickTime = SystemClock.elapsedRealtime()
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
                ColorToast(this@DonateParticipationActivity,"잠시 후 다시 접속해주세요")
            }

            override fun onResponse(call: Call<GetDonateRecordResponse>, response: Response<GetDonateRecordResponse>) {
                if (response.isSuccessful) {
                    Log.d("response", response.body()!!.status.toString())
                    if (response.body()!!.status == Secret.NETWORK_SUCCESS) {
                        Log.d("response", response.body()!!.data.toString())
                        response.body()!!.data?.donate?.let {
                            Log.d("hello", "im innocent")

                            total_berry.text = response.body()!!.data.donateBerry.toString()
                            participation_num.text = response.body()!!.data.donate.toString()

//                            if (response.body()!!.data.donate.toString().length <= 3)
//                                participation_num.text = response.body()!!.data.donate.toString()
//                            else
//                                total_berry.text = dec.format(response.body()!!.data.donateBerry).toString()
//
//                            if (response.body()!!.data.donateBerry.toString().length <= 3)
//                                total_berry.text = response.body()!!.data.donateBerry.toString()
//                            else
//                                participation_num.text = dec.format(response.body()!!.data.donate).toString()
                        }
                        if (response.body()!!.data?.donate == null) {
                            total_berry.text = 0.toString()
                            participation_num.text = 0.toString()
                        }
                    } else if (response.body()!!.status == 600) {
                        ColorToast(this@DonateParticipationActivity,response.body()!!.message)
                    }
                }
            }
        })
    }

    private fun getDonateBerryNum(){

        var dataList_donateberrynum = ArrayList<Int>()

        var token: String = SharedPreferenceController.getAuthorization(this)

        val getDonateParticipationBerryNumResponse =
            networkService.getDonateParticipationBerryNumResponse(token)

        getDonateParticipationBerryNumResponse.enqueue(object : Callback<GetDonateParticipationBerryNumResponse> {
            override fun onFailure(call: Call<GetDonateParticipationBerryNumResponse>, t: Throwable) {
                Log.e("hello", t.toString())
                ColorToast(this@DonateParticipationActivity, "잠시 후 다시 접속해주세요")
            }

            override fun onResponse(
                call: Call<GetDonateParticipationBerryNumResponse>,
                response: Response<GetDonateParticipationBerryNumResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == Secret.NETWORK_SUCCESS) {
                        val receiveData: ArrayList<DonateBerryData>? = response.body()?.data
                        Log.d("useberry" ,response.body()?.toString())
                        if (receiveData!!.size > 0) {
                            for (i in 0 until receiveData.size) {
                                Log.d("useberry" ,receiveData[i].berry.toString())
                                dataList_donateberrynum.add(receiveData[i].berry)
                                Log.d("useberry3", dataList_donateberrynum.size.toString() )
                            }
                            getDonateParticipationResponse(dataList_donateberrynum)
                        }
                    } else if (response.body()!!.status == 600) {
                        ColorToast(this@DonateParticipationActivity, response.body()!!.message)
                    }
                }
            }
        })
        dataList_donateberrynum.clear()
    }

    private fun getDonateParticipationResponse(berryList : ArrayList<Int>) {

        var token: String = SharedPreferenceController.getAuthorization(this)

        val getDonateParticipationResponse =
            networkService.getDonateParticipationResponse(token)

        getDonateParticipationResponse.enqueue(object : Callback<GetDonateParticipationResponse> {
            override fun onFailure(call: Call<GetDonateParticipationResponse>, t: Throwable) {
                Log.e("hello", t.toString())
                ColorToast(this@DonateParticipationActivity, "잠시 후 다시 접속해주세요")
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
                                dataList_donateParticipateInfo.add(
                                    DonateParticipationInfo(
                                        receiveData[i]._id,
                                        receiveData[i].thumbnail,
                                        "- " + converteDday(receiveData[i].finish),
                                        receiveData[i].title,
                                        receiveData[i].centerName,
                                        receiveData[i].percentage.toString(),
                                        berryList[i],
                                        receiveData[i].state
                                    )
                                )
                            }
                            updateDonateList(dataList_donateParticipateInfo)
                        }
                    } else if (response.body()!!.status == 600) {
                        ColorToast(this@DonateParticipationActivity,response.body()!!.message)
                    }
                }
            }
        })
    }

    private fun getmyBerryResponse() {
        var token: String = SharedPreferenceController.getAuthorization(this)

        val getmyBerryResponse =
            networkService.getmyBerryResponse("application/json", token)

        getmyBerryResponse.enqueue(object : Callback<GetmyBerryResponse> {
            override fun onFailure(call: Call<GetmyBerryResponse>, t: Throwable) {
                Log.d("No berry", "No Berry")
                ColorToast(this@DonateParticipationActivity, "잠시 후 다시 접속해주세요")
            }

            override fun onResponse(call: Call<GetmyBerryResponse>, response: Response<GetmyBerryResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == Secret.NETWORK_LIST_SUCCESS) {
                        val receiveData = response.body()?.data

                        val dec = DecimalFormat("#,000")
                        val dec_berry: String

                        if (receiveData.toString().length <= 3) {
                            dec_berry = receiveData.toString()
                        } else {
                            dec_berry = dec.format(receiveData)
                        }
                        drawer_myberry.text = dec_berry
                    }
                }
            }
        })
    }

    private fun updateDonateList(list: ArrayList<DonateParticipationInfo>) {
        dataList.clear()
        dataList.addAll(list)
        donateParticipationListRecyclerViewAdapter.notifyDataSetChanged()
    }

    private fun converteDday(finish: String): String {

        var dday: Int = 0
        var Dday: String = ""

        if (finish != null) {

            val today = Calendar.getInstance()
            val finishdateFormat = SimpleDateFormat("yyyy-MM-dd").parse(finish.split("T")[0])
            val instance: Calendar = Calendar.getInstance()
            instance.setTime(finishdateFormat)

            val cnt_today: Long = today.timeInMillis / 86400000
            val cnt_instance: Long = instance.timeInMillis / 86400000

            val sub: Long = cnt_today - cnt_instance

            dday = Math.abs(sub.toInt() + 1)
            Dday = "$dday"
        }
        return Dday
    }
}