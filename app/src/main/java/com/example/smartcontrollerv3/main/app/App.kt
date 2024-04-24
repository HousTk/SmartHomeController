package com.example.smartcontrollerv3.main.app

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.os.Build
import androidx.core.content.getSystemService
import com.example.smartcontrollerv3.main.di.appModule
import com.example.smartcontrollerv3.main.di.cacheModule
import com.example.smartcontrollerv3.main.di.dataModule
import com.example.smartcontrollerv3.main.di.domainModule
import com.example.smartcontrollerv3.main.di.firebaseModule
import kotlinx.coroutines.withContext
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.net.HttpURLConnection


class App():Application() {

    override fun onCreate() {
        super.onCreate()



        startKoin{

            androidLogger(Level.ERROR)
            androidContext(androidContext = this@App)
            modules(listOf(appModule, domainModule, dataModule, cacheModule, firebaseModule))

        }

    }


}