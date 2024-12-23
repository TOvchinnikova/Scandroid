package com.t_ovchinnikova.android.scandroid_2.scanner_impl.data.repository

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeEntity
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.data.datasource.ScannerDataSource
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.domain.repository.ScannerRepository
import kotlinx.coroutines.flow.SharedFlow

class ScannerRepositoryImpl(
    private val dataSource: ScannerDataSource
) : ScannerRepository {

    override fun getScannedCodeFlow(): SharedFlow<CodeEntity> {
        return dataSource.getScannedCodeFlow()
    }

    override fun setScannedCode(code: CodeEntity) {
        dataSource.setScannedCode(code)
    }
}