package com.t_ovchinnikova.android.scandroid_2

import android.app.Application
import com.t_ovchinnikova.android.scandroid_2.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ScandroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@ScandroidApplication)
            modules(appModules)
        }
    }
}