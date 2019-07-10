package jjackjjack.sopt.com.jjackjjack.network.response.get

import jjackjjack.sopt.com.jjackjjack.model.TotalDonateInfo

data class GettotalDonateResponse (
    val status: Int,
    val message: String,
    val data: ArrayList<TotalDonateInfo>
)