package jjackjjack.sopt.com.jjackjjack.network.response.get

import jjackjjack.sopt.com.jjackjjack.network.data.BerryHistoryData

data class GetBerryHistoryResponse(
    val status: Int,
    val message: String,
    val data: BerryHistory?
)

data class BerryHistory(
    val history: ArrayList<BerryHistoryData>?
)