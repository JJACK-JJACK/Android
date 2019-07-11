package jjackjjack.sopt.com.jjackjjack.activities.stamp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import jjackjjack.sopt.com.jjackjjack.network.response.get.GetStampResponse
import jjackjjack.sopt.com.jjackjjack.utillity.Secret.Companion.NETWORK_SUCCESS
import kotlinx.android.synthetic.main.activity_stamp.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StampActivity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stamp)

        btn_toolbar_back.setOnClickListener {
            finish()
        }

        stamp.setOnClickListener {
            getStampResponse()
        }
    }


    private fun getStampResponse() {
        var token: String = SharedPreferenceController.getAuthorization(this)
        Log.d("hi", "hi")

        val getStampResponse =
            networkService.getStampResponse(token)
        Log.d("hi1", "hi1")

        getStampResponse.enqueue(object : Callback<GetStampResponse> {
            override fun onFailure(call: Call<GetStampResponse>, t: Throwable) {
                Log.d("hi2", "hi2")
                Log.d("response fail", "response fail")
            }

            override fun onResponse(call: Call<GetStampResponse>, response: Response<GetStampResponse>) {
                if (response.isSuccessful) {
                    Log.d("hi3", "hi3")
                    if (response.body()!!.status == NETWORK_SUCCESS) {
                        Log.d("hi4", "hi4")
                        val receiveData = response.body()?.data

                        toast(receiveData.toString())
                    }

                }else{
                    toast("스탬프 조회 실패")
                }
            }
        })
    }
}