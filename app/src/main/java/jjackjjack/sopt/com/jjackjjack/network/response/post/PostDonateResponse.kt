package jjackjjack.sopt.com.jjackjjack.network.response.post

import jjackjjack.sopt.com.jjackjjack.network.data.DonateData

data class PostDonateResponse (
    val status: Int,
    val message: String,
    val data: ArrayList<DonateData>
)