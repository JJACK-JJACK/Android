package jjackjjack.sopt.com.jjackjjack.activities.deliveryreview

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.webkit.URLUtil
import com.bumptech.glide.Glide
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.list.DonateUsePlanRecyclerViewAdapter
import jjackjjack.sopt.com.jjackjjack.model.DonateUsePlan
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import jjackjjack.sopt.com.jjackjjack.network.data.DonatedDetailedData
import jjackjjack.sopt.com.jjackjjack.network.response.get.GetDonateParticipationDetailResponse
import jjackjjack.sopt.com.jjackjjack.utillity.ColorToast
import kotlinx.android.synthetic.main.activity_rank_berryreview.*
import kotlinx.android.synthetic.main.fragment_berryuse_review.*
import kotlinx.android.synthetic.main.fragment_use_berry.*
import kotlinx.android.synthetic.main.header_img.*
import kotlinx.android.synthetic.main.li_state.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class BerryReviewActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    val dataList: ArrayList<DonateUsePlan> by lazy {
        ArrayList<DonateUsePlan>()
    }

    val dataList_use_plan: ArrayList<DonateUsePlan> by lazy {
        ArrayList<DonateUsePlan>()
    }
    val dec = DecimalFormat("#,000")

    lateinit var donateUsePlanRecyclerViewAdapter: DonateUsePlanRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rank_berryreview)

        initialUI()

        donateUsePlanRecyclerViewAdapter = DonateUsePlanRecyclerViewAdapter(this, dataList)
        rv_donate_use_container.adapter = donateUsePlanRecyclerViewAdapter
        rv_donate_use_container.layoutManager = LinearLayoutManager(this)
    }

    private fun initialUI() {
        li_state_day.visibility = (View.INVISIBLE)
        li_state_total_num.visibility = (View.GONE)
        li_state_total_num_berry.visibility = (View.GONE)

        btn_toolbar_back.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        dataList_use_plan.clear()
        getDonateReviewResponse()
    }

    private fun getDonateReviewResponse() {

        val idx: Int = intent.getIntExtra("clickedItemIndex", 0)

        val getDonateReviewResponse =
            networkService.getDeliveryReviewResponse()

        getDonateReviewResponse.enqueue(object : Callback<GetDonateParticipationDetailResponse> {
            override fun onFailure(call: Call<GetDonateParticipationDetailResponse>, t: Throwable) {
                Log.d("hello", t.toString())
                ColorToast(this@BerryReviewActivity, "잠시 후 다시 접속해주세요")
            }

            override fun onResponse(
                call: Call<GetDonateParticipationDetailResponse>,
                response: Response<GetDonateParticipationDetailResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 201) {
                        val receiveData: ArrayList<DonatedDetailedData> = response.body()!!.data
                        if (idx + 1 <= receiveData.size) {
                            if (URLUtil.isValidUrl(receiveData[idx].thumbnail)) {
                                Glide.with(this@BerryReviewActivity).load(receiveData[idx].thumbnail)
                                    .into(header_image_view)
                            }
                            donate_detailed_title.text = receiveData[idx].title
                            donate_detailed_association.text = receiveData[idx].centerName
                            li_state_percent.text = receiveData[idx].percentage.toString()
                            li_state_berry_num.text = dec.format(receiveData[idx].totalBerry).toString()
                            li_state_progress.progress = receiveData[idx].percentage

                            li_state_progress.progressDrawable.setColorFilter(
                                Color.parseColor("#464fb2"),
                                PorterDuff.Mode.SRC_IN
                            )
                            li_state_percent_mark.setTextColor(Color.parseColor("#464fb2"))
                            li_state_percent.setTextColor(Color.parseColor("#464fb2"))

                            use_story_title.text = receiveData[idx].review[0].story!![0].subTitle
                            use_story_content1.text = receiveData[idx].review[0].story!![0].content!![0]
                            use_story_content2.text = receiveData[idx].review[0].story!![0].content!![1]

                            if (URLUtil.isValidUrl(receiveData[idx].review[0].story!![0].img)) {
                                Glide.with(this@BerryReviewActivity)
                                    .load(receiveData[idx].review[0].story!![0].img)
                                    .into(use_story_img)
                            }

                            var sum = 0

                            for (i in 0 until receiveData[idx].plan!!.size) {
                                dataList_use_plan.add(
                                    DonateUsePlan(
                                        (i + 1).toString(),
                                        receiveData[idx].plan!![i].purpose,
                                        dec.format(receiveData[idx].plan!![i].price).toString()
                                    )
                                )
                                sum = sum + receiveData[idx].plan!![i].price.toString().toInt()
                                updateDonateRecordStatus(dataList_use_plan)
                            }
                            use_berry_total.text = dec.format(sum).toString()
                        }
                    }
                }
            }
        })
    }

    private fun updateDonateRecordStatus(list: ArrayList<DonateUsePlan>) {
        dataList.clear()
        dataList.addAll(list)
        donateUsePlanRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
