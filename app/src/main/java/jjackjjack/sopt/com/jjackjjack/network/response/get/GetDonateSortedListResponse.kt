package jjackjjack.sopt.com.jjackjjack.network.response.get

import jjackjjack.sopt.com.jjackjjack.network.data.DonateSortedData

data class GetDonateSortedListResponse (
    val status: Int,
    val message: String,
    val data: ArrayList<DonateSortedData>
)