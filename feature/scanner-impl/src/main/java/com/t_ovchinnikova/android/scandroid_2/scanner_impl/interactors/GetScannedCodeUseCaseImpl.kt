package com.t_ovchinnikova.android.scandroid_2.scanner_impl.interactors

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.repository.ScannerRepository
import kotlinx.coroutines.flow.SharedFlow

class GetScannedCodeUseCaseImpl(
    private val repository: ScannerRepository
) : GetScannedCodeUseCase {

    override fun invoke(): SharedFlow<Code> {
        return repository.getScannedCodeFlow()
    }
}