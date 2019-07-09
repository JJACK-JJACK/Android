package jjackjjack.sopt.com.jjackjjack.network.response.post

import jjackjjack.sopt.com.jjackjjack.network.data.BerryChargeData

data class PostBerryChargeResponse (
    val status : Int,
    val message: String,
    val data: BerryChargeData
)
