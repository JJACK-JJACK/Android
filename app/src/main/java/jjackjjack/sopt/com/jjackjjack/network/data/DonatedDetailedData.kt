package jjackjjack.sopt.com.jjackjjack.network.data

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
    val reviewData: ArrayList<ReviewData>,

    val start: String,
    val finish: String,
    val deliver: String
)