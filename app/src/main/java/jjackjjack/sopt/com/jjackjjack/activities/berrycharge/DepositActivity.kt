package jjackjjack.sopt.com.jjackjjack.activities.berrycharge

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.activities.payment.PaymentActivity
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import kotlinx.android.synthetic.main.activity_berry_deposit.*
import org.json.JSONObject
import retrofit2.Callback
import retrofit2.Response
import jjackjjack.sopt.com.jjackjjack.network.response.post.PostBerryChargeResponse
import jjackjjack.sopt.com.jjackjjack.utillity.Secret
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call


class DepositActivity : AppCompatActivity(){
    val networkService: NetworkService by lazy{
        ApplicationController.instance.networkService
    }

    var clickTest = arrayOf(0, 0, 0, 0, 0)

    lateinit var ivList: Array<ImageView>

    lateinit var lyList: Array<LinearLayout>

    lateinit var berryList : Array<Int>

    var now_berry : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_berry_deposit)


        InitialUI()



    }


    private fun InitialUI(){

        btn_back.setOnClickListener {
            finish()
        }

        lyList = arrayOf(
            ly_deposit_radio10, ly_deposit_radio50, ly_deposit_radio100, ly_deposit_radio300, ly_deposit_radio500
        )
        ivList = arrayOf(
            deposit_img_1, deposit_img_2, deposit_img_3, deposit_img_4, deposit_img_5
        )
        berryList = arrayOf(
            10, 50, 100, 300, 500
        )


      for(i in 0 until lyList.size){
          lyList[i].setOnClickListener {
              clickTest[i] = 1 - clickTest[i]
              for(j in 0 until ivList.size) {
                  ivList[j].isSelected = false
                  now_berry = berryList[i]
              }
              ivList[i].isSelected=true

          }
      }


    }

    override fun onResume() {
        super.onResume()
        btn_berry_deposit_charge.setOnClickListener {
            if( clickTest.sum()>0){
                //계좌뜨는 다이얼로그 창
                postBerryChargeResponse()
            }
        }
    }

    private fun postBerryChargeResponse(){
        Log.d("hi2", "2")
        var jsonObject = JSONObject()
        jsonObject.put("chargeBerry", now_berry)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        val token = SharedPreferenceController.getAuthorization(this)

        Log.d("hi", token.toString())

        val postBerryChargeResponse:Call<PostBerryChargeResponse> =
            networkService.postBerryChargeResponse("application/json", token, gsonObject)

        Log.d("hi10", "10")

        postBerryChargeResponse.enqueue(object : Callback<PostBerryChargeResponse> {

            override fun onFailure(call: Call<PostBerryChargeResponse>, t: Throwable){
                Log.d("hi3", "3")
                Log.e("DB error", t.toString())
            }

            override fun onResponse(call: Call<PostBerryChargeResponse>, response: Response<PostBerryChargeResponse>){
                if(response.isSuccessful){
                    Log.d("hi4", "4")
                    if(response.body()!!.status == Secret.NETWORK_LIST_SUCCESS){
                        Log.d("hi5", "5")
                        //startActivity<PaymentActivity>()
                        finish()
                    }
                    else{

                    }
                }
            }
        })

    }





//        deposit_img_1.setOnClickListener {
//            if(deposit_img_1 != null){
//                deposit_img_1.isSelected = true
//                deposit_img_2.isSelected = false
//                deposit_img_3.isSelected = false
//                deposit_img_4.isSelected = false
//                deposit_img_5.isSelected = false
//            }
//        }
//        deposit_img_2.setOnClickListener {
//            if(deposit_img_2 != null){
//                deposit_img_2.isSelected = true
//                deposit_img_1.isSelected = false
//                deposit_img_3.isSelected = false
//                deposit_img_4.isSelected = false
//                deposit_img_5.isSelected = false
//            }
//        }
//        deposit_img_3.setOnClickListener {
//            if(deposit_img_3 != null){
//                deposit_img_3.isSelected = true
//                deposit_img_1.isSelected = false
//                deposit_img_2.isSelected = false
//                deposit_img_4.isSelected = false
//                deposit_img_5.isSelected = false
//            }
//        }
//        deposit_img_4.setOnClickListener {
//            if(deposit_img_4 != null){
//                deposit_img_4.isSelected = true
//                deposit_img_1.isSelected = false
//                deposit_img_3.isSelected = false
//                deposit_img_2.isSelected = false
//                deposit_img_5.isSelected = false
//            }
//        }
//        deposit_img_5.setOnClickListener {
//            if(deposit_img_5 != null){
//                deposit_img_5.isSelected = true
//                deposit_img_1.isSelected = false
//                deposit_img_3.isSelected = false
//                deposit_img_4.isSelected = false
//                deposit_img_2.isSelected = false
//            }
//        }
//    }
//    private fun configureState(){
//        if(deposit_img_1.isSelected) {
//            deposit_img_2.isSelected = false
//            deposit_img_3.isSelected = false
//            deposit_img_4.isSelected = false
//            deposit_img_5.isSelected = false
//        }


}




