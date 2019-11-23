package com.mashup.di

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
}