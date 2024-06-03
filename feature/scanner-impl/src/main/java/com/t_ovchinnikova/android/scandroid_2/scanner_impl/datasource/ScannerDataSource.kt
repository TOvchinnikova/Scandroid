package com.t_ovchinnikova.android.scandroid_2.scanner_impl.datasource

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import kotlinx.coroutines.flow.SharedFlow

interface ScannerDataSource {

    fun getScannedCodeFlow(): SharedFlow<Code>

    fun setScannedCode(code: Code)
}