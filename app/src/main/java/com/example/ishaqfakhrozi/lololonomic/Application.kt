package com.example.ishaqfakhrozi.lololonomic

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco


class Application : Application() {

    companion object {
        lateinit var instance:com.example.ishaqfakhrozi.lololonomic.Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Fresco.initialize(this)

    }
}