package jjackjjack.sopt.com.jjackjjack.activities.berrycharge

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import kotlinx.android.synthetic.main.activity_berry_deposit.*
import retrofit2.Callback
import retrofit2.Response
import jjackjjack.sopt.com.jjackjjack.network.response.post.PostBerryChargeResponse
import jjackjjack.sopt.com.jjackjjack.utillity.Secret

import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call


class DepositActivity : AppCompatActivity(){

    val networkService: NetworkService by lazy{
        ApplicationController.instance.networkService
    }

    var clickTest = arrayOf(0, 0, 0, 0, 0)

    lateinit var ivList: Array<ImageView>

    lateinit var lyList: Array<LinearLayout>

    lateinit var berryList : Array<Int>

    lateinit var berryList_money : Array<Int>

    var now_berry : Int = 0

    var now_money : Int = 0

    var deposit_list = ArrayList<String>()

    lateinit var selected_bank : String

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
        berryList_money = arrayOf(
            1100, 5500, 11000, 33000, 55000
        )

        deposit_list.add("국민은행 67070204087002")
        deposit_list.add("신한은행 110499030264")
        deposit_list.add("우리은행 1002659283912")
        deposit_list.add("하나은행 50391038504107")
        deposit_list.add("카카오뱅크 3333048166414")

        val deposit_adapter = ArrayAdapter(this@DepositActivity, R.layout.support_simple_spinner_dropdown_item, deposit_list)
        berry_deposit_spinner.adapter= deposit_adapter as SpinnerAdapter?

        berry_deposit_spinner.onItemSelectedListener=
            object :AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selected_bank = berry_deposit_spinner.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    toast("입금하실 은행을 선택해주세요")
                }
            }

      for(i in 0 until lyList.size){
          lyList[i].setOnClickListener {
              clickTest[i] = 1 - clickTest[i]
              for(j in 0 until ivList.size) {
                  ivList[j].isSelected = false
                  now_berry = berryList[i]
                  now_money = berryList_money[i]
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
                BerryChargeResponse()
            }
        }
    }

    private fun BerryChargeResponse(){
        var jsonObject = JSONObject()
        jsonObject.put("chargeBerry",now_berry)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        val token = SharedPreferenceController.getAuthorization(this)

        val postBerryChargeResponse:Call<PostBerryChargeResponse> =
            networkService.postBerryChargeResponse("application/json", token, gsonObject)

        postBerryChargeResponse.enqueue(object : Callback<PostBerryChargeResponse> {

            override fun onFailure(call: Call<PostBerryChargeResponse>, t: Throwable){
                Log.e("DB error", t.toString())
                toast("DB error")
            }

            override fun onResponse(call: Call<PostBerryChargeResponse>, response: Response<PostBerryChargeResponse>){
                if(response.isSuccessful){
                    if(response.body()!!.status == Secret.NETWORK_LIST_SUCCESS){
                        startActivity<PaymentActivity>("berry_charge" to now_money, "selected_bank" to selected_bank)
                        finish()
                    }
                    else{
                        toast("베리 충전 실패")
                    }
                }
            }
        })
    }
}





