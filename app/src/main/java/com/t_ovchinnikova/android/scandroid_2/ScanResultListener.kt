package com.t_ovchinnikova.android.scandroid_2

import com.t_ovchinnikova.android.scandroid_2.domain.Code

interface ScanResultListener {

    fun onScanned(result: Code)
}