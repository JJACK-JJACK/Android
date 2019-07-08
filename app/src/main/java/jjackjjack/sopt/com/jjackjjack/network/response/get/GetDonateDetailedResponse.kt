package jjackjjack.sopt.com.jjackjjack.network.response.get

import jjackjjack.sopt.com.jjackjjack.network.data.DonatedDetailedData

data class GetDonateDetailedResponse(
    val status: Int,
    val message: String,
    val data: ArrayList<DonatedDetailedData>
)