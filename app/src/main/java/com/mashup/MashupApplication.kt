package com.mashup

import android.app.Application
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen

class MashupApplication: Application() {

    companion object {
        @JvmStatic
        lateinit var instance: MashupApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        initJSR310()
        
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    private fun initJSR310() {
        AndroidThreeTen.init(this)
    }
}