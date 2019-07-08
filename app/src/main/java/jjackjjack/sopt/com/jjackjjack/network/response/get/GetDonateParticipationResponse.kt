package jjackjjack.sopt.com.jjackjjack.network.response.get

import jjackjjack.sopt.com.jjackjjack.network.data.DonatedDetailedData

class GetDonateParticipationResponse (
    val status: Int,
    val message: String,
    val data: ArrayList<DonatedDetailedData>?
)

