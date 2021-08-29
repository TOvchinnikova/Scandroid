package com.t_ovchinnikova.android.scandroid_2

import android.app.Application
import android.util.Log
import com.t_ovchinnikova.android.scandroid_2.data.CodeRepository

class ScandroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CodeRepository.initialize(this)
    }
}