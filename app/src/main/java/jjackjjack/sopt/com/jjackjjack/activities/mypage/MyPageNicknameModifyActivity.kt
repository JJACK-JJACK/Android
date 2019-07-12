package jjackjjack.sopt.com.jjackjjack.activities.mypage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jjackjjack.sopt.com.jjackjjack.R
import kotlinx.android.synthetic.main.activity_my_page_nickname_modify.*
import android.content.Intent
import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController.setUserNickname
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import jjackjjack.sopt.com.jjackjjack.network.response.post.PostNicknameCheckResponse
import jjackjjack.sopt.com.jjackjjack.network.response.post.PostNicknameResponse
import jjackjjack.sopt.com.jjackjjack.utillity.ColorToast
import jjackjjack.sopt.com.jjackjjack.utillity.Secret
import jjackjjack.sopt.com.jjackjjack.utillity.Secret.Companion.NETWORK_LIST_SUCCESS
import jjackjjack.sopt.com.jjackjjack.utillity.Secret.Companion.NETWORK_PW_FAIL
import org.jetbrains.anko.ctx
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyPageNicknameModifyActivity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    var nickname : String = ""

    private var send_nickname : String = ""

    private var duplicateCheck = false //중복확인체크 하고 넘어갔는지 아닌지

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page_nickname_modify)

        edt_nickname_modify.hint = intent.getStringExtra("nickname")

        btn_nick_back.setOnClickListener {
            finish()
        }

        btn_erase_all.setOnClickListener {
            edt_nickname_modify.setText(null)
        }

        btn_check.setOnClickListener {
            var edtString = edt_nickname_modify.text.toString()
            if(edtString ==  ""){
                edtString = intent.getStringExtra("nickname")
            }
            postNicknameResponse()
            val resultIntent = Intent()
            resultIntent.putExtra("nickname_changed", edtString)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        btn_nickname_duplicate.setOnClickListener {
            NicknameResponseData()
        }
    }
    private fun postNicknameResponse(){
        var token: String = SharedPreferenceController.getAuthorization(this)

        nickname = edt_nickname_modify.toString()

        var jsonObject = JSONObject()
        jsonObject.put("nickname", nickname)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postNicknameResponse =
                networkService.postNicknameResponse("application/json", token, gsonObject)

        postNicknameResponse.enqueue(object: Callback<PostNicknameResponse>{
            override fun onFailure(call: Call<PostNicknameResponse>, t: Throwable) {
                Log.d("Not Existence", t.toString())
            }

            override fun onResponse(call: Call<PostNicknameResponse>, response: Response<PostNicknameResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == NETWORK_LIST_SUCCESS){
                        ColorToast(ctx, "닉네임을 수정했습니다")
                    }
                }else if(response.body()!!.status == NETWORK_PW_FAIL){
                    ColorToast(ctx, "닉네임을 입력하세요")
                }
            }
        })
    }

    private fun NicknameResponseData(){
        nickname = edt_nickname_modify.toString()

        var jsonObject = JSONObject()
        jsonObject.put("nickname", send_nickname)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        val postNicknameCheckResponse: Call<PostNicknameCheckResponse> =
            networkService.postNicknameCheckResponse("application/json", gsonObject)

        postNicknameCheckResponse.enqueue(object: Callback<PostNicknameCheckResponse>{
            override fun onFailure(call: Call<PostNicknameCheckResponse>, t: Throwable) {
                Log.e("duplicate check fail", t.toString())
                toast("중복 확인 실패")
            }

            override fun onResponse(
                call: Call<PostNicknameCheckResponse>,
                response: Response<PostNicknameCheckResponse>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.status == Secret.NETWORK_SUCCESS){
                        ColorToast(ctx, "사용 가능한 닉네임입니다")
                        duplicateCheck = true
                        nickname = edt_nickname_modify.text.toString()
                        setUserNickname(this@MyPageNicknameModifyActivity, nickname)
                    }
                    else{
                        ColorToast(ctx, response.body()!!.message)
                        duplicateCheck = false
                    }
                }
            }
        })
    }
}
