package jjackjjack.sopt.com.jjackjjack.activities.stamp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.activities.donateparicipation.DonateParticipationActivity
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import jjackjjack.sopt.com.jjackjjack.network.response.get.GetStampResponse
import jjackjjack.sopt.com.jjackjjack.utillity.ColorToast
import jjackjjack.sopt.com.jjackjjack.utillity.Secret.Companion.NETWORK_SUCCESS
import kotlinx.android.synthetic.main.activity_stamp.*
import org.jetbrains.anko.image
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StampActivity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    lateinit var stampList: Array<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stamp)

        stampList = arrayOf(
            stamp_1, stamp_2, stamp_3, stamp_4, stamp_5, stamp_6, stamp_7, stamp_8, stamp_9
        )

        btn_toolbar_back.setOnClickListener {
            finish()
        }
        getStampResponse()
    }

    override fun onResume() {
        super.onResume()
        getStampResponse()
    }

    private fun getStampResponse() {
        var token: String = SharedPreferenceController.getAuthorization(this)

        val getStampResponse =
            networkService.getStampResponse(token)

        getStampResponse.enqueue(object : Callback<GetStampResponse> {
            override fun onFailure(call: Call<GetStampResponse>, t: Throwable) {
                Log.d("response fail", "response fail")
                ColorToast(this@StampActivity, "잠시 후 다시 접속해주세요")
            }

            override fun onResponse(call: Call<GetStampResponse>, response: Response<GetStampResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == NETWORK_SUCCESS) {
                        val receiveData = response.body()?.data
                        var currStamp = receiveData!!.cntStamp
                        for (i in 0 until currStamp) {
                            stampList[i].setImageResource(R.drawable.ic_bry_stamp)
                        }
                    }

                }else{
                    ColorToast(this@StampActivity, "스탬프 조회 실패")
                }
            }
        })
    }
}