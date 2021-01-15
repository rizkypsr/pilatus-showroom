package com.showroom.pilatus.network

import android.content.Context
import com.showroom.pilatus.PilatusShowroom
import com.showroom.pilatus.utils.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        PilatusShowroom.getApp().getToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }

}