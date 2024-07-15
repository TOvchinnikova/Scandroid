package com.t_ovchinnikova.android.scandroid_2.scanner_impl.domain.usecase.interactor

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.domain.repository.ScannerRepository
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.domain.usecase.GetScannedCodeUseCase
import kotlinx.coroutines.flow.SharedFlow

class GetScannedCodeUseCaseImpl(
    private val repository: ScannerRepository
) : GetScannedCodeUseCase {

    override fun invoke(): SharedFlow<Code> {
        return repository.getScannedCodeFlow()
    }
}