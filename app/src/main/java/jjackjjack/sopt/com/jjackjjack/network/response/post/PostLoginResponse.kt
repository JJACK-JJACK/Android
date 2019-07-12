package jjackjjack.sopt.com.jjackjjack.network.response.post

data class PostLoginResponse(
    val status : Int,
    val message: String,
    val data: LoginData
)
data class LoginData(
    val token: String,
    val nickname: String,
    val email: String,
    val profileImg: String?
)