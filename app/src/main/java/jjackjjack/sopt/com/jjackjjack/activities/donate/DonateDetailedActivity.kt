package jjackjjack.sopt.com.jjackjjack.activities.donate

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.webkit.URLUtil
import com.bumptech.glide.Glide
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.list.DonateStoryRecyclerViewAdapter
import jjackjjack.sopt.com.jjackjjack.list.DonateUsePlanRecyclerViewAdapter
import jjackjjack.sopt.com.jjackjjack.model.DonateUsePlan
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import jjackjjack.sopt.com.jjackjjack.network.data.DonatedDetailedData
import jjackjjack.sopt.com.jjackjjack.network.data.StoryData
import jjackjjack.sopt.com.jjackjjack.network.data.UsePlanData
import jjackjjack.sopt.com.jjackjjack.network.response.get.GetDonateDetailedResponse
import jjackjjack.sopt.com.jjackjjack.utillity.Constants
import jjackjjack.sopt.com.jjackjjack.utillity.Secret
import kotlinx.android.synthetic.main.activity_donate_detailed.*
import kotlinx.android.synthetic.main.header_img.*
import kotlinx.android.synthetic.main.li_state.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class DonateDetailedActivity : AppCompatActivity() {

//    var fragmentAdapter: DetailFragmentAdapter by Delegates.notNull()

    val networkService: NetworkService by lazy{
        ApplicationController.instance.networkService
    }

    lateinit var ProgramId : String

    private val storyList: ArrayList<StoryData> by lazy{
        ArrayList<StoryData>()
    }

    private val tempStoryList: ArrayList<StoryData> by lazy{
        ArrayList<StoryData>()
    }

    private val usePlanList : ArrayList<DonateUsePlan> by lazy{
        ArrayList<DonateUsePlan>()
    }

    private val tempUsePlanList : ArrayList<DonateUsePlan> by lazy {
        ArrayList<DonateUsePlan>()
    }

    val dec = DecimalFormat("#,000")

    lateinit var donateStoryRecyclerViewAdapter: DonateStoryRecyclerViewAdapter
    lateinit var donateUsePlanRecyclerViewAdapter: DonateUsePlanRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate_detailed)

        ProgramId = intent.getStringExtra("programId")
        initialUI()
        getDonateDetailResponse(ProgramId)

    }



    private fun getDonateDetailResponse(programId: String){ //programId 넘겨주기
        val getDonateDetailResponse = networkService.getDonateDetailedResponse(programId)

        getDonateDetailResponse.enqueue(object : Callback<GetDonateDetailedResponse>{
            override fun onFailure(call: Call<GetDonateDetailedResponse>, t: Throwable) {
                Log.e("Sorted List fail", t.toString())
            }

            override fun onResponse(
                call: Call<GetDonateDetailedResponse>,
                response: Response<GetDonateDetailedResponse>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.status == Secret.NETWORK_LIST_SUCCESS){
                        clearStoryDataList()
                        clearUsePlanDataList()
                        val temp: ArrayList<DonatedDetailedData> = response.body()!!.data //temp가 없을 때 터짐
                        li_state_d_day.text = temp[0].toDonateDetail().d_day
                        li_state_percent.text = temp[0].toDonateDetail().percentage.toString()
                        li_state_berry_num.text = dec.format(temp[0].toDonateDetail().totalBerry.toInt()
                        )
                        li_state_total_num.text = dec.format(temp[0].toDonateDetail().maxBerry.toInt())
                        li_state_progress.progress = temp[0].toDonateDetail().percentage
                        donate_detailed_title.text = temp[0].toDonateDetail().title
                        donate_detailed_association.text = temp[0].toDonateDetail().centerName

                        if(URLUtil.isValidUrl(temp[0].toDonateDetail().thumbnail)){
                            Glide.with(this@DonateDetailedActivity).load(temp[0].toDonateDetail().thumbnail).into(header_image_view)
                        }

                        val storytemp: ArrayList<StoryData> = temp[0].story
                        for(i in 0 until storytemp.size){
                            tempStoryList.add(storytemp[i])
                        }

                        tv_use_plan_maxberry.text = temp[0].toDonateDetail().maxBerry
                        val usePlantemp: ArrayList<UsePlanData> = temp[0].plan
                        for(i in 0 until usePlantemp.size){
                            tempUsePlanList.add(usePlantemp[i].toDonateUsePlan(i+1))
                        }

                        updateStoryDataList(tempStoryList)
                        updateUsePlanDataList(tempUsePlanList)
                    }
                    else{
                        toast(response.body()!!.message)
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

        ProgramId = intent.getStringExtra("programId")

        getDonateDetailResponse(ProgramId)
       // FragmentUI()



    }



    private fun initialUI(){
        btn_toolbar_back.setOnClickListener {
            finish()
        }

        donate_detailed_tab.addTab(donate_detailed_tab.newTab().setText("기부스토리"))
        donate_detailed_tab.addTab(donate_detailed_tab.newTab().setText("사용계획"))

        donate_detailed_tab.getTabAt(0)?.select()
        donate_detailed_tab.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    Constants.TAB_DONATE_STORY->{
                        ly_donate_detail_story.visibility = View.VISIBLE
                        ly_donate_detail_use_plan.visibility = View.GONE
                    }
                    Constants.TAB_USE_PLAN->{
                        ly_donate_detail_story.visibility = View.GONE
                        ly_donate_detail_use_plan.visibility = View.VISIBLE
                    }
                }
            }
        })

        btn_cheer.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = "text/plain"
            intent.addCategory(Intent.CATEGORY_DEFAULT)

            startActivity(Intent.createChooser(intent, "공유"))
        }

        btn_donate.setOnClickListener {
            startActivity<DonatePaymentActivity>("programId" to ProgramId)
        }


        donateStoryRecyclerViewAdapter = DonateStoryRecyclerViewAdapter(this, storyList)
        rv_donate_story.adapter = donateStoryRecyclerViewAdapter
        rv_donate_story.layoutManager = LinearLayoutManager(this)

        donateUsePlanRecyclerViewAdapter = DonateUsePlanRecyclerViewAdapter(this, usePlanList)
        rv_donate_use_plan_container.adapter = donateUsePlanRecyclerViewAdapter
        rv_donate_use_plan_container.layoutManager = LinearLayoutManager(this)

    }

    private fun updateStoryDataList(list: ArrayList<StoryData>){
        storyList.clear()
        storyList.addAll(list)
        donateStoryRecyclerViewAdapter.notifyDataSetChanged()
    }

    private fun updateUsePlanDataList(list: ArrayList<DonateUsePlan>){
        usePlanList.clear()
        usePlanList.addAll(list)
        donateUsePlanRecyclerViewAdapter.notifyDataSetChanged()
    }

    private fun clearStoryDataList(){
        tempStoryList.clear()
        storyList.clear()
        donateStoryRecyclerViewAdapter.notifyDataSetChanged()
    }

    private fun clearUsePlanDataList(){
        tempUsePlanList.clear()
        usePlanList.clear()
    }

}