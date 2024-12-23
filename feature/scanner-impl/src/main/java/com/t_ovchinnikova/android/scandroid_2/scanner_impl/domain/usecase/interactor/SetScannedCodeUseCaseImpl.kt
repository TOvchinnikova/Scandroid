package com.t_ovchinnikova.android.scandroid_2.scanner_impl.domain.usecase.interactor

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeEntity
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.domain.repository.ScannerRepository
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.domain.usecase.SetScannedCodeUseCase

class SetScannedCodeUseCaseImpl(
    private val repository: ScannerRepository
) : SetScannedCodeUseCase {

    override fun invoke(code: CodeEntity) {
        repository.setScannedCode(code)
    }
}