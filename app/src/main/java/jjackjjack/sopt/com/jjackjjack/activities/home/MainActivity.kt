package jjackjjack.sopt.com.jjackjjack.activities.home

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.View
import android.webkit.URLUtil
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.activities.stamp.GetBerryActivity
import jjackjjack.sopt.com.jjackjjack.activities.berrycharge.BerryChargeActivity
import jjackjjack.sopt.com.jjackjjack.activities.berryusehistory.BerryHistoryActivity
import jjackjjack.sopt.com.jjackjjack.activities.donate.DonateActivity
import jjackjjack.sopt.com.jjackjjack.activities.donateparicipation.DonateParticipationActivity
import jjackjjack.sopt.com.jjackjjack.activities.login.BeginningActivity
import jjackjjack.sopt.com.jjackjjack.activities.mypage.MyPageActivity
import jjackjjack.sopt.com.jjackjjack.activities.deliveryreview.DeliveryReviewActivity
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController
import jjackjjack.sopt.com.jjackjjack.interfaces.onDrawer
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import jjackjjack.sopt.com.jjackjjack.network.response.get.GetmyBerryResponse
import jjackjjack.sopt.com.jjackjjack.utillity.Constants
import jjackjjack.sopt.com.jjackjjack.utillity.Secret
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_activity_main.*
import kotlinx.android.synthetic.main.nav_drawer.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat


class MainActivity : AppCompatActivity(), onDrawer {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    lateinit var btnFset: Array<ImageView>

    lateinit var btnAset: Array<View>

    lateinit var actSet : Array<Class<out AppCompatActivity>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialUI()

        Log.d("token", SharedPreferenceController.getAuthorization(this))

        drawerUI()
        getmyBerryResponse()
    }

    override fun onResume() { //로그아웃 후에 이 뷰는 꺼지게
        super.onResume()
        if(!SharedPreferenceController.getAuthorization(this).isNotEmpty()){
            startActivity<BeginningActivity>()
            finish()
        }
    }

    private fun initialUI(){
        if(!SharedPreferenceController.getAuthorization(this).isNotEmpty()){
            startActivity<BeginningActivity>()
            finish()
        }

        var fragmentAdapter =
            MainActivityImageSliderAdapter(this, supportFragmentManager)
        main_activity_slider_pager.adapter = fragmentAdapter

        main_activity_slider_pager.setClipToPadding(false)
        val dpValue = 40
        val d = resources.displayMetrics.density
        val margin = (dpValue * d).toInt()
        main_activity_slider_pager.setPadding(margin, 0, margin, 0)
        main_activity_slider_pager.setPageMargin(margin / 2)

        var fragmentList = ArrayList<FragmentMainActivityImageSlider>()

        for (i in 0..5) {
            var fragmentMainActivityImageSlider =
                FragmentMainActivityImageSlider()
            fragmentMainActivityImageSlider.setCategoryNum(i)
            fragmentList.add(fragmentMainActivityImageSlider)
            fragmentAdapter.addImage(fragmentList[i], i)
        }

        fragmentAdapter.notifyDataSetChanged()
    }
    override fun drawerUI(){
        getmyBerryResponse()
        actSet = arrayOf(
            MainActivity::class.java, DonateParticipationActivity::class.java,
            DeliveryReviewActivity::class.java, MyPageActivity::class.java,
            BerryChargeActivity::class.java
        )

        btnFset = arrayOf( //프래그먼트로 가는 버튼
            btn_drawer_f_child, btn_drawer_f_senior, btn_drawer_f_animal, btn_drawer_f_disabled,
            btn_drawer_f_env, btn_drawer_f_emergency
        )
        btnAset = arrayOf(
            btn_drawer_home, btn_drawer_donate_record, btn_drawer_rank,
            btn_drawer_mypage, btn_drawer_berrycharge
        )
        drawerBtnSetting(Constants.ACTIVITY_MAIN)
    }

    override fun drawerBtnSetting(activityType: Int){
        btn_hambuger.setOnClickListener {
            if(!ly_drawer.isDrawerOpen(Gravity.END)){
                ly_drawer.openDrawer(Gravity.END)
            }
            Log.d("nickname", tv_drawer_nickname.toString())
        }

        btn_cancel.setOnClickListener {
            if(ly_drawer.isDrawerOpen(Gravity.END)){
                ly_drawer.closeDrawer(Gravity.END)
            }
        }


        tv_drawer_nickname.text = SharedPreferenceController.getUserNickname(this)//닉네임 DB 저장한 거 가져오는거
        tv_drawer_email.text = SharedPreferenceController.getUserEmail(this) // 이메일 DB 저장한 거
        if(URLUtil.isValidUrl(SharedPreferenceController.getUserImg(this))){
            Glide.with(this).load(SharedPreferenceController.getUserImg(this))
                .apply(RequestOptions.circleCropTransform())?.into(iv_drawer_profileimg)
        } //이미지 DB에서 가져오기 나중에 없을때 default 이미지 뜨게 처리해야함

        btn_drawer_usehistory.setOnClickListener {
            startActivity<BerryHistoryActivity>()
        }


        for(i in 0 until btnAset.size){
            btnAset[i].setOnClickListener{
                val intent = Intent(this, actSet[i])
                ly_drawer.closeDrawer(Gravity.END)
                Handler().postDelayed({startActivity(intent)}, 110)
                if(activityType == i){
                    finish()
                }
            }
        }

        for(i in 0 until btnFset.size){
            btnFset[i].setOnClickListener {
                startActivity<DonateActivity>("fragment" to i)
                Handler().postDelayed({ ly_drawer.closeDrawer(Gravity.END)}, 110)
            }
        }
    }

    override fun onBackPressed() {
        if(ly_drawer.isDrawerOpen(Gravity.END)){
            ly_drawer.closeDrawer(Gravity.END)
        }
        else{
            super.onBackPressed()
        }
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
