package com.mashup.di

import com.mashup.api.fcm.FcmService
import com.mashup.api.notice.NoticeService
import com.mashup.api.user.UserService
import org.koin.dsl.module
import retrofit2.Retrofit

val ApiModule = module {
    single<NoticeService>(createdAtStart = false) { get<Retrofit>().create(NoticeService::class.java) }
    single<UserService>(createdAtStart = false) { get<Retrofit>().create(UserService::class.java) }
    single<FcmService>(createdAtStart = false) { get<Retrofit>().create(FcmService::class.java) }
}
