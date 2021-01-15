package com.showroom.pilatus.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.internal.`$Gson$Preconditions`
import com.showroom.pilatus.R

class SessionManager(context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "token_pref"
    }

    /**
     * Function to save auth token
     */
    fun saveToken(token: String) {
        val editor = preferences.edit()
        editor.putString(PREFS_NAME, token)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return preferences.getString(PREFS_NAME, null)
    }

    /**
     * Function to remove auth token
     */
    fun removeToken() {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }

}