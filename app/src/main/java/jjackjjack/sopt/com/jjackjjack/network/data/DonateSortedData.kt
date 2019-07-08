package jjackjjack.sopt.com.jjackjjack.network.data

import jjackjjack.sopt.com.jjackjjack.model.DonateInfo

data class DonateSortedData(
    val categoryId: Int, //ㅇ
    val filterId: Int, //ㅇ
    val _id: String, //ㅇ =programId

    val title: String, //ㅇ
    val centerName: String, //ㅇ
    val thumbnail: String, //ㅇ

    val story: ArrayList<StoryData>?,//ㄴ
    val plan: ArrayList<UsePlanData>?,//ㄴ
    val state: Int, //ㅇ, 색깔
    val totalBerry: Int, //ㄴ
    val maxBerry: Int,//ㅇ
    val percentage: Int,//ㅇ
    val review: ArrayList<ReviewData>, //이거 기부하기_홈에서는 안쓰일듯

    val start: String, // D-day 계산
    val finish: String,
    val deliver: String
){
    fun toDonateInfo() : DonateInfo{
        var d = DonateInfo(_id = _id, thumnail = "", d_day = "", title = "",
            centerName = "", percent = "", maxBerry = "")
        d.thumnail = thumbnail
        d.title = title
        d.centerName = centerName
        d.percent = percentage.toString()
        d.maxBerry = maxBerry.toString()
        d.d_day = converteDday(start, finish).toString()
        return d
    }

    private fun converteDday(start: String, finish: String) : Int{
        var Dday : Int = 0

        var convertedDate_start = StringBuilder()
        var convertedDate_finish = StringBuilder()

        if(start != null && finish != null){
            val dateInfoList_start = start.split("T")
            val dateInfoList_finish = finish.split("T")

        }

        return Dday
    }
}





