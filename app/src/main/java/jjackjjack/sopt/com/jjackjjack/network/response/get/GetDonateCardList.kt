package jjackjjack.sopt.com.jjackjjack.network.response.get

import jjackjjack.sopt.com.jjackjjack.data.DonateInfoData

data class GetDonateCardList (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: ArrayList<DonateInfoData>?
)