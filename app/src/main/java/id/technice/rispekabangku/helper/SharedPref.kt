package id.technice.rispekabangku.helper

import android.content.Context
import android.content.SharedPreferences


class SharedPref(context : Context) {

    private val mypref = "MAIN_PREF"
    private val sp: SharedPreferences = context.getSharedPreferences(mypref, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sp.edit()
    private val freshInstalation = "freshInstalation"
    private val languange = "languange"



    fun isFresh(value : Boolean){
        sp.edit().putBoolean(freshInstalation, value).apply()
    }

    fun isFresh(): Boolean {
        return sp.getBoolean(freshInstalation, true)
    }

    fun setLanguange(value : String){
        sp.edit().putString(languange, value).apply()
    }

    fun getLanguange(): String {
        return sp.getString(languange, "id") ?: "id"
    }
}