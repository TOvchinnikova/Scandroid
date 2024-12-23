package com.t_ovchinnikova.android.scandroid_2.scanner_impl.data.datasource

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeEntity
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ScannerDataSourceImpl : ScannerDataSource {

    private val codeFlow: MutableSharedFlow<CodeEntity> = MutableSharedFlow(replay = 1)

    override fun getScannedCodeFlow(): SharedFlow<CodeEntity> {
        return codeFlow.asSharedFlow()
    }

    override fun setScannedCode(code: CodeEntity) {
        codeFlow.tryEmit(code)
    }
}