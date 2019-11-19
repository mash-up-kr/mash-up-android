package com.mashup.di

import com.mashup.repository.DefaultNoticesRepository
import com.mashup.repository.NoticesRepository
import com.mashup.repository.source.remote.RepositoriesRemoteDataSource
import org.koin.dsl.module

val ApplicationModule = module {
    single { RepositoriesRemoteDataSource(get()) }
    single { DefaultNoticesRepository(get()) as NoticesRepository }
}