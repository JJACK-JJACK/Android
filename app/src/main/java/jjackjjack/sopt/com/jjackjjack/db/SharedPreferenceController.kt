

package jjackjjack.sopt.com.jjackjjack.db

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceController{
    private val USER_NAME = "user_name"

    private val USER_EMAIL:String = "user_email"
    private val USER_PW:String= "user_pw"

    //email 집어 넣기
    fun setUserEmail(ctx: Context, input_email: String){
        val preference: SharedPreferences = ctx.getSharedPreferences(USER_NAME,Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putString(USER_EMAIL, input_email)
        editor.commit()
    }

    //pw 집어 넣기
    fun setUserPW(ctx:Context, input_pw: String){
        val preference: SharedPreferences = ctx.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putString(USER_PW, input_pw)
        editor.commit()
    }

    //ID 꺼내기
    fun getUserID(ctx: Context):String{
        val preference: SharedPreferences = ctx.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE)
        return preference.getString(USER_EMAIL, "")//(키 명, 든게 없을때 리턴할 값)
    }

    fun getUserPW(ctx: Context):String{
        val preference: SharedPreferences = ctx.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE)
        return preference.getString(USER_PW, "")
    }

    fun clearUserSharedPreferences(ctx: Context){
        val preference: SharedPreferences = ctx.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preference.edit()
        editor.clear()
        editor.commit()
    }
}