package jjackjjack.sopt.com.jjackjjack.network.data


import jjackjjack.sopt.com.jjackjjack.model.DonateInfo
import jjackjjack.sopt.com.jjackjjack.model.DonateUsePlan
import java.text.DecimalFormat

data class UsePlanData(
    val purpose: String,
    val price: Int
) {
    fun toDonateUsePlan(i: Int): DonateUsePlan {

        val dec = DecimalFormat("#,000")

        var d = DonateUsePlan(idx = i.toString(), plan_detail = "", berry_num = "")
        d.plan_detail = purpose
        d.berry_num = dec.format(price.toInt())

        return d
    }
}
