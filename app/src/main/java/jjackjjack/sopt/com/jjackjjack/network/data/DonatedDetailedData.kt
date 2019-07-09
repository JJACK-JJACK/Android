package jjackjjack.sopt.com.jjackjjack.network.data

import jjackjjack.sopt.com.jjackjjack.model.DonateRecordStatusInfo

data class DonatedDetailedData(

    val _id: String,
    val title: String,
    val categoryId: Int,

    val centerName: String,
    val thumbnail: String,

    val story: ArrayList<StoryData>?,
    val plan: ArrayList<UsePlanData>?,
    val state: Int,
    val totalBerry: Int,
    val maxBerry: Int,
    val percentage: Int,
    val review: ArrayList<ReviewData>,


    val start: String,
    val finish: String,
    val deliver: String
)
{
    fun toDonateRecordStatusInfo() : DonateRecordStatusInfo {
        var dr = DonateRecordStatusInfo(_id=_id, thumbnail = "", title = "",
            centerName = "", percentage = "", totalBerry = "", maxBerry = "")
        dr.thumbnail = thumbnail
        dr.title = title
        dr.centerName = centerName
        dr.percentage = percentage.toString()
        dr.totalBerry = totalBerry.toString()
        dr.maxBerry = maxBerry.toString()
        return dr
    }
}

