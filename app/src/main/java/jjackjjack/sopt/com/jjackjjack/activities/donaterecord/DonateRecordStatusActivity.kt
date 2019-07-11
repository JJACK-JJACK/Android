package jjackjjack.sopt.com.jjackjjack.activities.donaterecord

import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.webkit.URLUtil
import com.bumptech.glide.Glide
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController
import jjackjjack.sopt.com.jjackjjack.model.DonateUsePlan
import jjackjjack.sopt.com.jjackjjack.list.DonateUsePlanRecyclerViewAdapter
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import jjackjjack.sopt.com.jjackjjack.network.data.DonatedDetailedData
import jjackjjack.sopt.com.jjackjjack.network.response.get.GetDonateParticipationDetailResponse
import jjackjjack.sopt.com.jjackjjack.utillity.Secret
import kotlinx.android.synthetic.main.activity_donate_record_status.*
import kotlinx.android.synthetic.main.donate_status.*
import kotlinx.android.synthetic.main.fragment_berryuse_review.*
import kotlinx.android.synthetic.main.fragment_participation_status.*
import kotlinx.android.synthetic.main.fragment_use_berry.*
import kotlinx.android.synthetic.main.header_img.*
import kotlinx.android.synthetic.main.li_state.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DonateRecordStatusActivity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    val dataList: ArrayList<DonateUsePlan> by lazy {
        ArrayList<DonateUsePlan>()
    }
    val dataList_DonateRecordStatus: ArrayList<DonateUsePlan> by lazy {
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

//        li_state_berry_num.text = intent.getStringExtra("maxBerry")
//        li_state_d_day.text = intent.getStringExtra("d_day")
//        li_state_percent.text = intent.getStringExtra("percent")
//        li_state_progress.progress = intent.getStringExtra("percent").toInt()
//        donate_detailed_title.text = intent.getStringExtra("title")
//        donate_detailed_association.text = intent.getStringExtra("centerName")

        var list: ArrayList<DonateUsePlan> = ArrayList()
        initialUI_status()
    }

    private fun getDonateParticipateDetailResponse() {

        val programId: String = intent.getStringExtra("programId_status")

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
                            for (i in 0 until receiveData.size) {
                                if (URLUtil.isValidUrl(receiveData[i].thumbnail)) {
                                    Glide.with(this@DonateRecordStatusActivity).load(receiveData[i].thumbnail)
                                        .into(header_image_view)
                                }
                                donate_detailed_title.text = receiveData[i].title
                                donate_detailed_association.text = receiveData[i].centerName
                                li_state_percent.text = receiveData[i].percentage.toString()
                                li_state_berry_num.text = receiveData[i].totalBerry.toString()
                                li_state_total_num.text = receiveData[i].maxBerry.toString()
                                li_state_progress.progress = receiveData[i].percentage

                                if (receiveData[i].state == 0) {
                                    circle_status_ing.setImageResource(R.drawable.shape_donate_status_circle_ing)
                                    donate_record_status_review.visibility = View.GONE
                                    li_state_progress.progressDrawable.setColorFilter(
                                        Color.parseColor("#da4830"),
                                        PorterDuff.Mode.SRC_IN
                                    )
                                    li_state_percent_mark.setTextColor(Color.parseColor("#da4830"))
                                    li_state_percent.setTextColor(Color.parseColor("#da4830"))
                                    tv_participation_ing.setTextColor(Color.parseColor("#da4830"))
                                    participation_status_ing.setTextColor(Color.parseColor("#333333"))
                                } else if (receiveData[i].state == 1) {
                                    circle_status_end.setImageResource(R.drawable.shape_donate_status_circle_end)
                                    li_state_d_mark.visibility = View.INVISIBLE
                                    li_state_d_day.visibility = View.INVISIBLE
                                    donate_record_status_review.visibility = View.GONE
                                    li_state_progress.progressDrawable.setColorFilter(
                                        Color.parseColor("#86b854"),
                                        PorterDuff.Mode.SRC_IN
                                    )
                                    li_state_percent_mark.setTextColor(Color.parseColor("#86b854"))
                                    li_state_percent.setTextColor(Color.parseColor("#86b854"))
                                    tv_participation_end.setTextColor(Color.parseColor("#86b854"))
                                    participation_status_end.setTextColor(Color.parseColor("#333333"))
                                } else if (receiveData[i].state == 2) {
                                    circle_status_finish.setImageResource(R.drawable.shape_donate_status_circle_finish)
                                    li_state_d_mark.visibility = View.INVISIBLE
                                    li_state_d_day.visibility = View.INVISIBLE
                                    li_state_total_num.visibility = View.INVISIBLE
                                    li_state_total_num_berry.visibility = View.INVISIBLE
                                    li_state_progress.progressDrawable.setColorFilter(
                                        Color.parseColor("#464fb2"),
                                        PorterDuff.Mode.SRC_IN
                                    )
                                    li_state_percent_mark.setTextColor(Color.parseColor("#464fb2"))
                                    li_state_percent.setTextColor(Color.parseColor("#464fb2"))
                                    tv_participation_finish.setTextColor(Color.parseColor("#464fb2"))
                                    participation_status_finish.setTextColor(Color.parseColor("#333333"))

                                    if (receiveData[0].review.size > 0) { //수정해야함
                                        donate_record_status_review.visibility = View.VISIBLE

                                        use_story_title.text = receiveData[i].review[i].story!![i].subTitle
                                        use_story_content1.text = receiveData[i].review[i].story!![i].content!![0]
                                        use_story_content2.text = receiveData[i].review[i].story!![i].content!![1]

                                        if (URLUtil.isValidUrl(receiveData[i].review[i].story!![i].img)) {
                                            Glide.with(this@DonateRecordStatusActivity)
                                                .load(receiveData[i].review[i].story!![i].img)
                                                .into(use_story_img)
                                        }
                                    }

                                    var sum = 0

                                    for (z in 0 until receiveData[i].plan!!.size) {
                                        dataList_DonateRecordStatus.add(
                                            DonateUsePlan(
                                                (z + 1).toString(),
                                                receiveData[i].plan!![z].purpose,
                                                receiveData[i].plan!![z].price.toString()
                                            )
                                        )
                                        sum = sum + receiveData[i].plan!![z].price.toString().toInt()
                                        updateDonateRecordStatus(dataList_DonateRecordStatus)
                                    }
                                    use_berry_total.text = sum.toString()
                                }
                            }
                        }
                    }
                }
            }
        })
    }

    override fun onResume() {
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