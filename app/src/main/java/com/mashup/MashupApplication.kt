package com.mashup

import android.app.Application

class MashupApplication: Application() {

    companion object {
        @JvmStatic
        lateinit var instance: MashupApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}