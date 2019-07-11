package jjackjjack.sopt.com.jjackjjack.network.data


import jjackjjack.sopt.com.jjackjjack.model.DonateDetail
import java.text.SimpleDateFormat
import java.util.*

import kotlin.collections.ArrayList

import jjackjjack.sopt.com.jjackjjack.model.DonateRecordStatusInfo


data class DonatedDetailedData(

    val _id: String,//ㅇ
    val title: String,//ㅇ
    val categoryId: Int,//ㄴ

    val centerName: String,//ㅇ
    val thumbnail: String,//ㅇ


    val story: ArrayList<StoryData>,//ㅇ-리사이클러
    val plan: ArrayList<UsePlanData>,//ㅇ-리사이클러
    val state: Int,//ㅇ
    val totalBerry: Int,//ㅇ
    val maxBerry: Int,//ㅇ
    val percentage: Int,//ㅇ
    val review: ArrayList<ReviewData>,//ㄴ

    val start: String,
    val finish: String,
    val deliver: String
){
    fun toDonateDetail(): DonateDetail{
        var d = DonateDetail(_id =_id, title = "", centerName = "",
            thumbnail = "", totalBerry = "", maxBerry = "", percentage = 0, d_day = "")

        d.title = title
        d.centerName = centerName
        d.thumbnail = thumbnail
        d.totalBerry = totalBerry.toString()
        d.maxBerry = maxBerry.toString()
        d.percentage = percentage
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
            else{
                Dday = "$dday"
            }
        }
        return Dday
    }
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


