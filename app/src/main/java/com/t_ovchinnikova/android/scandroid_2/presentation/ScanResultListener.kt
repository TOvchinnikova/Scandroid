package com.t_ovchinnikova.android.scandroid_2.presentation

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code

interface ScanResultListener {

    fun onScanned(resultCode: Code)
}