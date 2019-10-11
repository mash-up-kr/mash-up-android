package com.mashup

import android.app.Application
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen
import com.mashup.app.notices.noticeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MashupApplication: Application() {

    companion object {
        @JvmStatic
        lateinit var instance: MashupApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(this@MashupApplication)
            modules(noticeModule)
        }
        initJSR310()
        
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    private fun initJSR310() {
        AndroidThreeTen.init(this)
    }
}