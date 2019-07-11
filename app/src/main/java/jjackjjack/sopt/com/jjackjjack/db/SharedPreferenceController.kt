

package jjackjjack.sopt.com.jjackjjack.db

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceController{
    private val USER_NAME = "MY_KEY"

    private val MYAUTH = "myAuth"
    private val NICKNAME = "nickname"
    private val USER_EMAIL:String = "user_email"
    private val USER_IMG: String ="user_img"

    //토큰 저장, 받아오기
    fun setAuthorization(context: Context, authorization: String){
        val pref = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(MYAUTH, authorization)
        editor.commit()
    }

    fun getAuthorization(context: Context) : String{
        val pref = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE)
        return pref.getString(MYAUTH, "")
    }

    //닉네임 저장, 받아오기
    fun setUserNickname(context:Context, nickname: String){
        val pref = context.getSharedPreferences(NICKNAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(NICKNAME, nickname)
        editor.commit()
    }

    fun getUserNickname(context:Context): String{
        val pref = context.getSharedPreferences(NICKNAME, Context.MODE_PRIVATE)
        return pref.getString(NICKNAME, "")
    }

    //이메일 저장, 받아오기
    fun setUserEmail(context: Context, email: String){
        val pref = context.getSharedPreferences(USER_EMAIL, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(USER_EMAIL, email)
        editor.commit()
    }

    fun getUserEmail(context: Context): String{
        val pref = context.getSharedPreferences(USER_EMAIL, Context.MODE_PRIVATE)
        return pref.getString(USER_EMAIL, "")
    }

    //이미지 저장, 받아오기
    fun setUserImg(context: Context, img: String?){
        val pref = context.getSharedPreferences(USER_IMG, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(USER_IMG, img)
        editor.commit()
    }

    fun getUserImg(context: Context): String?{
        val pref = context.getSharedPreferences(USER_IMG, Context.MODE_PRIVATE)
        return pref.getString(USER_IMG, "")
    }

    //로그아웃할 떄 DB 청소
    fun clearUserSharedPreferences(ctx: Context){
        val preference: SharedPreferences = ctx.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preference.edit()
        editor.clear()
        editor.commit()
    }
}