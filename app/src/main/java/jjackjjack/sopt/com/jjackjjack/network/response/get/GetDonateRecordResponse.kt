package jjackjjack.sopt.com.jjackjjack.network.response.get

import jjackjjack.sopt.com.jjackjjack.network.data.DonateRecordData

data class GetDonateRecordResponse (
    val status: Int,
    val message: String,
    val data: DonateRecordData
)