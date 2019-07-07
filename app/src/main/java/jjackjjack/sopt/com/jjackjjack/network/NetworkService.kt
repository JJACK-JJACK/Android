package jjackjjack.sopt.com.jjackjjack.network

import com.google.gson.JsonObject
import jjackjjack.sopt.com.jjackjjack.network.response.post.PostLoginResponse
import jjackjjack.sopt.com.jjackjjack.network.response.post.PostNicknameCheckResponse
import jjackjjack.sopt.com.jjackjjack.network.response.post.PostSignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface NetworkService {

    //----------로그인/회원가입---------
    //로그인
   @POST("api/user/signin")
    fun postLoginResponse(
        @Header("Content-Type") content_type: String,
        @Body() body: JsonObject
    ): Call<PostLoginResponse>

    //회원가입
    @POST("api/user/signup")
    fun postSignupResponse(
        @Header("Content-Type") content_type: String,
        @Body() body: JsonObject
    ): Call<PostSignUpResponse>

    //닉네임 중복 검사
    @POST("api/user/nickname")
    fun postNicknameCheckResponse(
        @Header("Content-Type") content_type: String,
        @Body() body: JsonObject
    ): Call<PostNicknameCheckResponse>

    //------기부이력---------
    //스탬프 수 조회
    //@GET("api//donation/stamp")
    //fun getStampResponse(
    //    @Header("Content-type") content_type: String,
    //    @Body() body: JsonObject
    //): Call<GetStampResponse>
}