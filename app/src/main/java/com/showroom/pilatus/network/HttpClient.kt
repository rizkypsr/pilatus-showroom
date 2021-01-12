package com.showroom.pilatus.network

import androidx.viewbinding.BuildConfig
import com.showroom.pilatus.PilatusShowroom
import com.showroom.pilatus.utils.Helpers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
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
        val token = PilatusShowroom.getApp().getToken()
        buildRetrofitClient(token)
    }

    fun buildRetrofitClient(token: String?) {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(2, TimeUnit.MINUTES)
        builder.readTimeout(2, TimeUnit.MINUTES)

        if (BuildConfig.DEBUG) {
            var interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }

        if (token != null) {
            builder.addInterceptor(getInterceptorWithHeader("Authorization", "Bearer ${token}"))
        }

        val okHttpClient = builder.build()
        client = Retrofit.Builder()
            .baseUrl("http://192.168.0.103:8888/pilatus-server/public/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Helpers.getDefaultGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        endPoint = null
    }

    private fun getInterceptorWithHeader(headerName: String, headerValue: String): Interceptor {
        val header = HashMap<String, String>()
        header.put(headerName, headerValue)
        return getInterceptorWithHeader(header)
    }

    private fun getInterceptorWithHeader(headers: Map<String, String>): Interceptor {
        return Interceptor {
            val original = it.request()
            val builder = original.newBuilder()
            for ((key, value) in headers) {
                builder.addHeader(key, value)
            }
            builder.method(original.method(), original.body())
            it.proceed(builder.build())
        }
    }
}