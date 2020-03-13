package com.gdc.mymoneymanager.data

import android.content.Context
import android.content.SharedPreferences
import com.gdc.mymoneymanager.util.Constants.KEY_IS_LOGIN
import com.gdc.mymoneymanager.util.Constants.KEY_USERNAME
import com.gdc.mymoneymanager.util.Constants.PREF_NAME

class PrefsManager {
    companion object {
        private fun getSharedPreference(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }

        fun setUsername(context: Context, username: String) {
            val editor = getSharedPreference(context).edit()
            editor.putString(KEY_USERNAME, username)
            editor.apply()
        }

        fun getUsername(context: Context): String {
            return getSharedPreference(context).getString(KEY_USERNAME, "").toString()
        }

        fun saveLoginState(context: Context, isLogin: Boolean) {
            val editor = getSharedPreference(context).edit()
            editor.putBoolean(KEY_IS_LOGIN, isLogin)
            editor.apply()
        }

        fun isLogin(context: Context): Boolean {
            return getSharedPreference(context).getBoolean(KEY_IS_LOGIN, false)
        }

        fun clearData(context: Context) {
            val editor = getSharedPreference(context).edit()
            editor.clear()
            editor.apply()
        }
    }
}