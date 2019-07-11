package jjackjjack.sopt.com.jjackjjack.activities.donate

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.activities.berrycharge.BerryChargeActivity
import jjackjjack.sopt.com.jjackjjack.activities.stamp.StampActivity
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import jjackjjack.sopt.com.jjackjjack.network.data.DonateData
import jjackjjack.sopt.com.jjackjjack.network.response.get.GetmyBerryResponse
import jjackjjack.sopt.com.jjackjjack.network.response.post.PostDonateResponse
import jjackjjack.sopt.com.jjackjjack.utillity.Secret
import kotlinx.android.synthetic.main.activity_donate_payment.*
import kotlinx.android.synthetic.main.activity_donate_payment.view.*
import kotlinx.android.synthetic.main.activity_mypage_berryhistory.*
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

    var currStamp = 0
    var currMyBerry = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate_payment)
        initialUI()

        getmyBerryResponse()
    }

    private fun initialUI() {
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
            var finaledtString = edt_donate_berry_num.text.toString()
            if (finaledtString.toInt() <= currMyBerry && finaledtString.toInt() != 0) {
                toast("donate start!")
                postDonateResponse(edtString)
                if (currStamp != 10) {
                    val builder = AlertDialog.Builder(this)
                    val dialogView = layoutInflater.inflate(R.layout.dialog_donate_payment, null)
                    val dialogbutton = dialogView.findViewById<Button>(R.id.btn_close)
                    builder.setView(dialogView)
                    builder.show()
                    dialogbutton.setOnClickListener {
                        finish()
                    }
                } else {
                    startActivity<StampActivity>()
                }
            }
        }
        btn_berry_charge.setOnClickListener {
            startActivity<BerryChargeActivity>()
        }
    }

    private fun postDonateResponse(donateBerry: String) {

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
            }

            override fun onResponse(call: Call<PostDonateResponse>, response: Response<PostDonateResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 201) {
                        toast("success")
                        val receiveData: DonateData? = response.body()!!.data
                        Log.d("berry", receiveData!!.totalBerry.toString())
                        currStamp = receiveData!!.stamps
                    }
                } else if (response.body()!!.status == 600) {
                    toast(response.body()!!.message)
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
}
