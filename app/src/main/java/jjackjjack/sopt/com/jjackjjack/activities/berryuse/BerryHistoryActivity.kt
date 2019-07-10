package jjackjjack.sopt.com.jjackjjack.activities.berryuse

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import jjackjjack.sopt.com.jjackjjack.network.response.get.BerryHistory
import jjackjjack.sopt.com.jjackjjack.network.response.get.GetBerryHistoryResponse
import jjackjjack.sopt.com.jjackjjack.network.response.get.GetmyBerryResponse
import jjackjjack.sopt.com.jjackjjack.utillity.Secret.Companion.NETWORK_LIST_SUCCESS
import kotlinx.android.synthetic.main.activity_mypage_berryhistory.*
import kotlinx.android.synthetic.main.fragment_berryuse_review.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class BerryHistoryActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    val dataList: ArrayList<BerryHistoryItem> by lazy {
        ArrayList<BerryHistoryItem>()
    }

    val dataList_berryhistory: ArrayList<BerryHistoryItem> by lazy {
        ArrayList<BerryHistoryItem>()
    }

    lateinit var berryhistoryAdapter: BerryHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_berryhistory)

        berryhistoryAdapter = BerryHistoryAdapter(this, dataList)

        rv_berryhistory.adapter = berryhistoryAdapter

        val lm = LinearLayoutManager(this)
        rv_berryhistory.layoutManager = lm
        lm.setOrientation(LinearLayoutManager.VERTICAL)
        rv_berryhistory.setHasFixedSize(true)

        getBerryHistoryResponse()
        getmyBerryResponse()

        btn_toolbar_back.setOnClickListener {
            finish()
        }
    }

    private fun getBerryHistoryResponse() {
        var token: String = SharedPreferenceController.getAuthorization(this)

        val getBerryHistoryResponse =
            networkService.getBerryHistoryResponse(token)

        getBerryHistoryResponse.enqueue(object : Callback<GetBerryHistoryResponse> {
            override fun onFailure(call: Call<GetBerryHistoryResponse>, t: Throwable) {
                Log.d("DB error", "DB error")
            }

            override fun onResponse(call: Call<GetBerryHistoryResponse>, response: Response<GetBerryHistoryResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 201) {
                        val receiveData: BerryHistory? = response.body()?.data
                        for (i in 0 until receiveData?.history!!.size) {
                            if(receiveData.history[i].centerName == null){
                                dataList_berryhistory.add(
                                    BerryHistoryItem(
                                        receiveData.history[i].title,
                                        receiveData.history[i].date,
                                        receiveData.history[i].berry,
                                        ""
                                    )
                                )
                            }
                            else {
                                dataList_berryhistory.add(
                                    BerryHistoryItem(
                                        receiveData.history[i].title,
                                        receiveData.history[i].date,
                                        receiveData.history[i].berry,
                                        receiveData.history[i].centerName!!
                                    )
                                )
                            }
                        }
                        updateDataList(dataList_berryhistory)
                    }
                } else if (response.body()!!.status == 600) {
                    toast(response.body()!!.message)
                }
            }
        })
    }

    private fun getmyBerryResponse(){
        var token:String = SharedPreferenceController.getAuthorization(this)

        val getmyBerryResponse =
                networkService.getmyBerryResponse("application/json",token)

        getmyBerryResponse.enqueue(object : Callback<GetmyBerryResponse>{
            override fun onFailure(call: Call<GetmyBerryResponse>, t: Throwable) {
                Log.d("No berry", "No Berry")
            }

            override fun onResponse(call: Call<GetmyBerryResponse>, response: Response<GetmyBerryResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == NETWORK_LIST_SUCCESS){
                        val receiveData = response.body()?.data

                        val dec = DecimalFormat("#,000")
                        val dec_berry : String

                        if(receiveData.toString().length <= 3){
                            dec_berry = receiveData.toString()
                        }else{
                            dec_berry = dec.format(receiveData)
                        }

                        berryhistory_myberry.text = dec_berry
                    }
                }
            }
        })
    }
    private fun updateDataList(list: ArrayList<BerryHistoryItem>){
        dataList.clear()
        dataList.addAll(list)
        berryhistoryAdapter.notifyDataSetChanged()
    }
}

