package jjackjjack.sopt.com.jjackjjack.network.response.get

data class GetStampResponse (
    val status: Int,
    val message: String,
    val data: stampData
)

data class stampData(
    val cntStamp : Int
)