package com.t_ovchinnikova.android.scandroid_2

import android.app.Application

class ScandroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        CodeRepository.initialize(this)
    }
}