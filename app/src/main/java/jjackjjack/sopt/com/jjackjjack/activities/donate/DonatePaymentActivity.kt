package jjackjjack.sopt.com.jjackjjack.activities.donate

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Button
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.activities.berrycharge.BerryChargeActivity
import jjackjjack.sopt.com.jjackjjack.activities.stamp.GetBerryActivity
import jjackjjack.sopt.com.jjackjjack.activities.stamp.StampActivity
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import jjackjjack.sopt.com.jjackjjack.network.data.DonateData
import jjackjjack.sopt.com.jjackjjack.network.response.get.GetmyBerryResponse
import jjackjjack.sopt.com.jjackjjack.network.response.post.PostDonateResponse
import jjackjjack.sopt.com.jjackjjack.utillity.ColorToast
import jjackjjack.sopt.com.jjackjjack.utillity.Secret
import kotlinx.android.synthetic.main.activity_donate_payment.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat


class DonatePaymentActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    //var currStamp = 0
    var currMyBerry = 0
    //var rewardBerry = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate_payment)
        initialUI()

        getmyBerryResponse()
    }

    private fun initialUI() {
        var currStamp = 0
        var edtString = edt_donate_berry_num.text.toString()
        btn_erase_all.setOnClickListener {
            edtString = "0"
            edt_donate_berry_num.setText(null)
        }

        btn_payment_back.setOnClickListener {
            finish()
        }

        btn_plus_10_berry.setOnClickListener {
            if (edtString.length > 0) {
                edtString = (edtString.toInt() + 10).toString()
            } else {
                edtString = "10"
            }
            edt_donate_berry_num.setText(edtString)
        }
        btn_plus_20_berry.setOnClickListener {
            if (edtString.length > 0) {
                edtString = (edtString.toInt() + 20).toString()
            } else {
                edtString = "20"
            }
            edt_donate_berry_num.setText(edtString)
        }
        btn_plus_50_berry.setOnClickListener {
            if (edtString.length > 0) {
                edtString = (edtString.toInt() + 50).toString()
            } else {
                edtString = "50"
            }
            edt_donate_berry_num.setText(edtString)
        }
        btn_plus_all_berry.setOnClickListener {
            edtString = currMyBerry.toString()
            edt_donate_berry_num.setText(edtString)
        }

        btn_donate.setOnClickListener {

            var finaledtString = edt_donate_berry_num?.text.toString()
            if (finaledtString != "") {
                if (finaledtString.toInt() <= currMyBerry) {
                    postDonateResponse(finaledtString)
                }
                else{
                    ColorToast(this, "보유 베리가 부족합니다")
                }
            }
            else{
                ColorToast(this, "베리를 입력해주세요")
            }
        }
        btn_berry_charge.setOnClickListener {
            startActivity<BerryChargeActivity>()
        }
    }

    override fun onResume() {
        super.onResume()
        getmyBerryResponse()
    }

    private fun postDonateResponse(donateBerry: String){

        var currStamp: Int = 0
        val programId: String = intent.getStringExtra("programId")
        var token: String = SharedPreferenceController.getAuthorization(this)

        var jsonObject = JSONObject()
        jsonObject.put("donateBerry", donateBerry)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postDonateResponse =
            networkService.postDonateResponse(token, programId, gsonObject)

        postDonateResponse.enqueue(object : Callback<PostDonateResponse> {
            override fun onFailure(call: Call<PostDonateResponse>, t: Throwable) {
                Log.d("hello", t.toString())
                ColorToast(this@DonatePaymentActivity, "잠시 후 다시 접속해주세요")
            }

            override fun onResponse(call: Call<PostDonateResponse>, response: Response<PostDonateResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 201) {
                        val receiveData: DonateData? = response.body()!!.data
                        Log.d("berry", receiveData!!.totalBerry.toString())
                        currStamp = receiveData!!.stamps
                        ColorToast(this@DonatePaymentActivity, "기부되었습니다!")
                        if (currStamp == 10) {
                            ctx.startActivity<GetBerryActivity>(
                                "rewardBerry" to receiveData!!.rewordsBerry
                            )
                            finish()
                        }
                        else{
                            showDialog()
                        }
                    }
                } else if (response.body()!!.status == 600) {
                    ColorToast(this@DonatePaymentActivity, response.body()!!.message)
                }
            }
        })
    }

    private fun getmyBerryResponse() {
        var token: String = SharedPreferenceController.getAuthorization(this)
        var currStamp: Int

        val getmyBerryResponse =
            networkService.getmyBerryResponse("application/json", token)

        getmyBerryResponse.enqueue(object : Callback<GetmyBerryResponse> {
            override fun onFailure(call: Call<GetmyBerryResponse>, t: Throwable) {
                Log.d("No berry", "No Berry")
                ColorToast(this@DonatePaymentActivity, "잠시 후 다시 접속해주세요")
            }

            override fun onResponse(call: Call<GetmyBerryResponse>, response: Response<GetmyBerryResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == Secret.NETWORK_LIST_SUCCESS) {
                        val receiveData = response.body()?.data

                        val dec = DecimalFormat("#,000")
                        val dec_berry: String

                        currMyBerry = receiveData!!

                        if (receiveData.toString().length <= 3) {
                            dec_berry = receiveData.toString()
                        } else {
                            dec_berry = dec.format(receiveData)
                        }
                        payment_myberry.text = dec_berry
                    }
                }
            }
        })
    }

    private fun showDialog(){
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_donate_payment, null)

        val btn_dialog_close = dialogView.findViewById<Button>(R.id.btn_close)
        val btn_dialog_check_stamp = dialogView.findViewById<Button>(R.id.btn_check)

        builder.setView(dialogView)
        builder.show()

        btn_dialog_check_stamp.setOnClickListener {
            ctx.startActivity<StampActivity>()
            finish()
        }

        btn_dialog_close.setOnClickListener {
            finish()
        }

    }
}
