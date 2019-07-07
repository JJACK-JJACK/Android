package jjackjjack.sopt.com.jjackjjack.network

import com.google.gson.JsonObject
import jjackjjack.sopt.com.jjackjjack.network.response.get.GetDonateCardList
import jjackjjack.sopt.com.jjackjjack.network.response.post.PostLoginResponse
import jjackjjack.sopt.com.jjackjjack.network.response.post.PostNicknameCheckResponse
import jjackjjack.sopt.com.jjackjjack.network.response.post.PostSignUpResponse
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface NetworkService {

    //----------로그인/회원가입---------
    //로그인
    @POST("/user/signin")
    fun postLoginResponse(
        @Header("Content-Type") content_type: String,
        @Body() body: JsonObject
    ): Call<PostLoginResponse>

    //회원가입
    @POST("/user/signup")
    fun postSignupResponse(
        @Header("Content-Type") content_type: String,
        @Body() body: JsonObject
    ): Call<PostSignUpResponse>

    //닉네임 중복 검사
    @POST("/user/nickname")
    fun postNicknameCheckResponse(
        @Header("Content-Type") content_type: String,
        @Body() body: JsonObject
    ): Call<PostNicknameCheckResponse>

    //홈 카테고리별 기부 카드 조회
    @GET("/program/:categoryId")
    fun getDonateCardList(
        @Path("categoryId") categoryId : Int
    ): Call<GetDonateCardList>

//    //홈 카테고리별 기부 카드 필터링 //굳이 나눌 필요가,,?
//    @GET("/program/:categoryId/:filterId")
//    fun getFilteredDonateCardList(
//        @Path("categoryId") categoryId : Int,
//        @Path("filterId") filterId : Int
//    ): Call<GetFilteredDonateCardList>

}
