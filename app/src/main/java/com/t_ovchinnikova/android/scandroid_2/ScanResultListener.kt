package com.t_ovchinnikova.android.scandroid_2

import com.t_ovchinnikova.android.scandroid_2.model.Code

interface ScanResultListener {

    fun onScanned(result: Code)
}