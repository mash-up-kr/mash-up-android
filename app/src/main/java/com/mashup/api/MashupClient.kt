package com.mashup.api

import com.mashup.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object MashupClient {

    const val BASE_URL = BuildConfig.BASE_URL
    private val TIMEOUT_CONNECT = 15L

    private val retrofit: Retrofit
    private val serviceMap = HashMap<Class<*>, Any>()

    init {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logger)
            .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T: Any> getService(classType: Class<T>): T {
        return if (serviceMap.contains(classType)) {
            serviceMap[classType] as T
        } else {
            retrofit.create(classType).apply {
                serviceMap[classType] = this
            }
        }
    }
}