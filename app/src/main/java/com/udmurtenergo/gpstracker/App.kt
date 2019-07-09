package com.udmurtenergo.gpstracker

import android.app.Application

class App : Application() {
    lateinit var injector: Injector

    companion object {
        private lateinit var instance: App
        fun getInstance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        injector = Injector(this)
    }
}
