package com.appmachine.hahmisto

import android.app.Application
import com.appmachine.hahmisto.data.AppContainer
import com.appmachine.hahmisto.data.AppDataContainer

class HahmistoApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}