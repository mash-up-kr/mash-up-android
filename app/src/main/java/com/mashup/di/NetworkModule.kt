package com.mashup.di

import com.google.gson.GsonBuilder
import com.mashup.BuildConfig
import com.mashup.util.EnumConverterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.mashup.util.ErrorInterceptor



private const val BASE_URL = BuildConfig.BASE_URL
private const val TIMEOUT_CONNECT = 15L

val NetworkModule = module {
    single { Cache(androidApplication().cacheDir, 10L * 1024 * 1024) }

    single {
        OkHttpClient.Builder().apply {
            cache(get())
            connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            addInterceptor(ErrorInterceptor())
            addInterceptor(HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            })
        }.build()
    }

    single { GsonBuilder().create() }

    single {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(EnumConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(get()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(get())
                .build()
    }
}
