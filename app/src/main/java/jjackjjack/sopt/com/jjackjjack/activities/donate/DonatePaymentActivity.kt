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
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import jjackjjack.sopt.com.jjackjjack.network.data.DonateData
import jjackjjack.sopt.com.jjackjjack.network.response.post.PostDonateResponse
import jjackjjack.sopt.com.jjackjjack.utillity.Secret
import kotlinx.android.synthetic.main.activity_donate_payment.*
import kotlinx.android.synthetic.main.activity_donate_payment.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DonatePaymentActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate_payment)

        initialUI()

    }

    private fun initialUI(){
        btn_donate.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_donate_payment, null)
            val dialogbutton = dialogView.findViewById<Button>(R.id.btn_close)
            builder.setView(dialogView)
            builder.show()
            dialogbutton.setOnClickListener{
                finish()
            }
        }

        btn_berry_charge.setOnClickListener {
            startActivity<BerryChargeActivity>()
        }
        var edtString = edt_donate_berry_num.text.toString()
        btn_erase_all.setOnClickListener {
            edtString = "0"
            edt_donate_berry_num.setText(null)
        }

        btn_payment_back.setOnClickListener {
            finish()
        }



        btn_plus_10_berry.setOnClickListener {
            if(edtString.length > 0){
                edtString = (edtString.toInt() + 10).toString()
            }
            else{
                edtString = "10"
            }
            edt_donate_berry_num.setText(edtString)
        }
        btn_plus_20_berry.setOnClickListener {
            if(edtString.length > 0){
                edtString = (edtString.toInt() + 50).toString()
            }
            else{
                edtString = "20"
            }
            edt_donate_berry_num.setText(edtString)
        }
        btn_plus_50_berry.setOnClickListener {
            if(edtString.length > 0){
                edtString = (edtString.toInt() + 50).toString()
            }
            else{
                edtString = "50"
            }
            edt_donate_berry_num.setText(edtString)
        }

    }

    private fun postDonateResponse() {

        var token: String = SharedPreferenceController.getAuthorization(this)

        var jsonObject = JSONObject()
        jsonObject.put("donateBerry", 2500)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postDonateResponse =
            networkService.postDonateResponse(token,"5d21f1ca41cb4a7984d17911",gsonObject)

        postDonateResponse.enqueue(object : Callback<PostDonateResponse> {
            override fun onFailure(call: Call<PostDonateResponse>, t: Throwable) {
                Log.d("hello", t.toString())
            }

            override fun onResponse(call: Call<PostDonateResponse>, response: Response<PostDonateResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == Secret.NETWORK_SUCCESS) {
                        val receiveData: ArrayList<DonateData> = response.body()!!.data
                        //추가 예정
                    }
                } else if (response.body()!!.status == 600) {
                    toast(response.body()!!.message)
                }
            }
        })
    }
}
