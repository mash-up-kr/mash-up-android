package com.mashup

import android.app.Application
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen
import com.mashup.app.attendees.AttendeesModule
import com.mashup.app.login.LoginModule
import com.mashup.app.noticedetail.NoticeDetailModule
import com.mashup.app.notices.NoticeModule
import com.mashup.app.setting.SettingModule
import com.mashup.di.ApiModule
import com.mashup.di.ApplicationModule
import com.mashup.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MashupApplication : Application() {

    companion object {
        @JvmStatic
        lateinit var instance: MashupApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(this@MashupApplication)
            modules(
                    listOf(
                            ApplicationModule,
                            ApiModule,
                            NetworkModule,
                            NoticeModule,
                            NoticeDetailModule,
                            AttendeesModule,
                            LoginModule,
                            SettingModule
                    ))
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