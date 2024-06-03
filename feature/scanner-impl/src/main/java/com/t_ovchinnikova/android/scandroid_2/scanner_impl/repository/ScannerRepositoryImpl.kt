package com.t_ovchinnikova.android.scandroid_2.scanner_impl.repository

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.datasource.ScannerDataSource
import kotlinx.coroutines.flow.SharedFlow

class ScannerRepositoryImpl(
    private val dataSource: ScannerDataSource
) : ScannerRepository {

    override fun getScannedCodeFlow(): SharedFlow<Code> {
        return dataSource.getScannedCodeFlow()
    }

    override fun setScannedCode(code: Code) {
        dataSource.setScannedCode(code)
    }
}