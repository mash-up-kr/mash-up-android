package com.mashup

import android.app.Application
import com.mashup.app.notices.NoticeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(NoticeModule)
        }
    }
}