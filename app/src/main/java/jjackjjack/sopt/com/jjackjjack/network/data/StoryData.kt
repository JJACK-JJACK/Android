package jjackjjack.sopt.com.jjackjjack.network.data

import android.util.Log
import jjackjjack.sopt.com.jjackjjack.model.DonateStory
import kotlin.properties.Delegates

data class StoryData(
    val subTitle: String,
    val content: ArrayList<String>,
    val img: String
){
    fun toDonateStory(): DonateStory{

        var d = DonateStory(subTitle = "", content = content, img = "")
        d.subTitle = subTitle
        d.img = img

        return d
    }
}