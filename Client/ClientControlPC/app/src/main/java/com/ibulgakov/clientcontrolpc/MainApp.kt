package com.ibulgakov.clientcontrolpc

import android.app.Application
import com.ibulgakov.clientcontrolpc.utils.Prefs
import com.ibulgakov.clientcontrolpc.utils.RxBus
import com.jakewharton.threetenabp.AndroidThreeTen

class MainApp: Application() {
    companion object {
        lateinit var instance: MainApp
            private set

        val globalBus = RxBus<Any>()

    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        Prefs.init(this)
        AndroidThreeTen.init(this)
    }
}
