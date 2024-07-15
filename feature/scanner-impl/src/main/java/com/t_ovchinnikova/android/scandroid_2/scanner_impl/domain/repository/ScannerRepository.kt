package com.t_ovchinnikova.android.scandroid_2.scanner_impl.domain.repository

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import kotlinx.coroutines.flow.SharedFlow

interface ScannerRepository {

    fun getScannedCodeFlow(): SharedFlow<Code>

    fun setScannedCode(code: Code)
}