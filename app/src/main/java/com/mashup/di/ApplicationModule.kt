package com.mashup.di

import com.google.firebase.messaging.FirebaseMessagingService
import com.mashup.fcm.MyFirebaseMessagingService
import com.mashup.repository.fcm.DefaultFcmRepository
import com.mashup.repository.fcm.FcmRepository
import com.mashup.repository.fcm.remote.FcmRemoteDataSource
import com.mashup.repository.notice.DefaultNoticesRepository
import com.mashup.repository.notice.NoticesRepository
import com.mashup.repository.notice.remote.NoticesRemoteDataSource
import com.mashup.repository.user.DefaultUserRepository
import com.mashup.repository.user.UserRepository
import com.mashup.repository.user.remote.UserRemoteDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val ApplicationModule = module {
    single { NoticesRemoteDataSource(get()) }
    single { DefaultNoticesRepository(get()) as NoticesRepository }

    single { UserRemoteDataSource(get()) }
    single { DefaultUserRepository(androidContext(), get()) as UserRepository }

    single { FcmRemoteDataSource(get()) }
    single { DefaultFcmRepository(androidContext(), get()) as FcmRepository }
}