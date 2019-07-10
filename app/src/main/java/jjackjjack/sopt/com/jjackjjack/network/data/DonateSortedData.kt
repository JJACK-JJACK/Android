package jjackjjack.sopt.com.jjackjjack.network.data

import jjackjjack.sopt.com.jjackjjack.model.DonateInfo
import jjackjjack.sopt.com.jjackjjack.model.DonateRecordStatusInfo
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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
        var d = DonateInfo(_id = _id, thumbnail = "", d_day = "", title = "",
            centerName = "", percent = "", maxBerry = "")
        d.thumbnail = thumbnail
        d.title = title
        d.centerName = centerName
        d.percent = percentage.toString()
        d.maxBerry = maxBerry.toString()
        d.d_day = converteDday(finish).toString()
        return d
    }

    private fun converteDday(finish: String) : String{

        var dday : Int = 0
        var Dday: String = ""


        if(finish != null) {

            val today = Calendar.getInstance()
            val finishdateFormat = SimpleDateFormat("yyyy-MM-dd").parse(finish.split("T")[0])
            val instance: Calendar = Calendar.getInstance()
            instance.setTime(finishdateFormat)

            val cnt_today: Long = today.timeInMillis / 86400000
            val cnt_instance: Long = instance.timeInMillis / 86400000

            val sub: Long = cnt_today - cnt_instance

            dday = sub.toInt() + 1

            if(dday >0){
                Dday = "+$dday"
            }
            else if(dday == 0){
                Dday = "-$dday"
            }
            else{
                Dday = "$dday"
            }
        }
        return Dday

    }
}





