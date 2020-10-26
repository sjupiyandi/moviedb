package id.sisco.themoviedb.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson


class SPManager(context: Context?) {
    val SP_APP = "spMovieDB"
    val SP_SUDAH_LOGIN = "spLogin"
    val SP_USER_ID = "spUserId"
    val sp: SharedPreferences

    init {
        sp = context!!.getSharedPreferences(SP_APP, Context.MODE_PRIVATE)
    }

    private fun saveSPString(keySP: String, value: String?) {
        val spEditor = sp.edit()
        spEditor.putString(keySP, value)
        spEditor.apply()
    }

    private fun saveSPLong(keySP: String, value: Long) {
        val spEditor = sp.edit()
        spEditor.putLong(keySP, value)
        spEditor.apply()
    }

    fun saveSPInt(keySP: String, value: Int) {
        val spEditor = sp.edit()
        spEditor.putInt(keySP, value)
        spEditor.apply()
    }

    private fun saveSPBoolean(keySP: String, value: Boolean) {
        val spEditor = sp.edit()
        spEditor.putBoolean(keySP, value)
        spEditor.apply()
    }

    fun saveSPSudahLogin(value: Boolean) {
        saveSPBoolean(SP_SUDAH_LOGIN, value)
    }

    fun getSPSudahLogin(): Boolean {
        return sp.getBoolean(SP_SUDAH_LOGIN, false)
    }

    fun saveSPUserId(value: String) {
        saveSPString(SP_USER_ID, value)
    }

    fun getSPUserId(): String? {
        return sp.getString(SP_USER_ID, "")
    }

}