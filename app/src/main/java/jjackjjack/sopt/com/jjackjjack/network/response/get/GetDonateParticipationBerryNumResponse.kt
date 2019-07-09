package jjackjjack.sopt.com.jjackjjack.network.response.get

import jjackjjack.sopt.com.jjackjjack.network.data.DonateBerryData

data class GetDonateParticipationBerryNumResponse(
    val status: Int,
    val message: String,
    val data: ArrayList<DonateBerryData>?
)
