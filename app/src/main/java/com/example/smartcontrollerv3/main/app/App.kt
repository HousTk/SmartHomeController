package com.example.smartcontrollerv3.main.app

import android.app.Application
import com.example.smartcontrollerv3.main.di.appModule
import com.example.smartcontrollerv3.main.di.dataModule
import com.example.smartcontrollerv3.main.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App():Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{

            androidLogger(Level.ERROR)
            androidContext(androidContext = this@App)
            modules(listOf(appModule, domainModule, dataModule))

        }

    }



}