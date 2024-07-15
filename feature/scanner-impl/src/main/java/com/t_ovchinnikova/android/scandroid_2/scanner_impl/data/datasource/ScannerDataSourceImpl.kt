package com.t_ovchinnikova.android.scandroid_2.scanner_impl.data.datasource

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ScannerDataSourceImpl : ScannerDataSource {

    private val codeFlow: MutableSharedFlow<Code> = MutableSharedFlow(replay = 1)

    override fun getScannedCodeFlow(): SharedFlow<Code> {
        return codeFlow.asSharedFlow()
    }

    override fun setScannedCode(code: Code) {
        codeFlow.tryEmit(code)
    }
}