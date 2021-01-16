package com.showroom.pilatus.network

import com.showroom.pilatus.utils.Constants
import com.showroom.pilatus.utils.Helpers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HttpClient {

    private var client: Retrofit? = null
    private var endPoint: ApiService? = null

    companion object {
        private val instance: HttpClient = HttpClient()

        @Synchronized
        fun getInstance(): HttpClient {
            return instance
        }
    }

    init {
        buildRetrofitClient()
    }

    fun getApi(): ApiService? {
        if (endPoint == null) {
            endPoint = client!!.create(ApiService::class.java)
        }
        return endPoint
    }

    private fun buildRetrofitClient() {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(2, TimeUnit.MINUTES)
        builder.readTimeout(2, TimeUnit.MINUTES)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)

        builder.addInterceptor(AuthInterceptor())

        val okHttpClient = builder.build()
        client = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Helpers.getDefaultGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        endPoint = null
    }
}