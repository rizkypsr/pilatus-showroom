package com.showroom.pilatus

import android.content.SharedPreferences
import androidx.multidex.MultiDexApplication
import androidx.preference.PreferenceManager
import com.showroom.pilatus.network.HttpClient

class PilatusShowroom : MultiDexApplication() {

    companion object {
        lateinit var instance: PilatusShowroom

        fun getApp(): PilatusShowroom {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun getPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(this)
    }

    fun setToken(token: String) {
        getPreferences().edit().putString("PREFERENCES_TOKEN", token).apply()
    }

    fun getToken(): String? {
        return getPreferences().getString("PREFERENCES_TOKEN", null)
    }

    fun setUser(user: String) {
        getPreferences().edit().putString("PREFERENCES_USER", user).apply()
    }

    fun getUser(): String? {
        return getPreferences().getString("PREFERENCES_USER", null)
    }

    fun removeToken() {
        getPreferences().edit().clear().apply()
    }
}