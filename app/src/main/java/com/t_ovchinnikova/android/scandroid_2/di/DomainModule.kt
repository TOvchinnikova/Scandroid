package com.t_ovchinnikova.android.scandroid_2.di

import com.google.mlkit.vision.barcode.BarcodeScanning
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.*
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors.RecognizeCodeInteractor
import org.koin.dsl.module

val domainModule = module {

    factory<AddCodeUseCase> {
        AddCodeUseCase(repository = get())
    }

    factory<DeleteAllCodesUseCase> {
        DeleteAllCodesUseCase(repository = get())
    }

    factory<DeleteCodeUseCase> {
        DeleteCodeUseCase(repository = get())
    }

    factory<GetCodesUseCase> {
        GetCodesUseCase(repository = get())
    }

    factory<GetCodesWithFilterUseCase> {
        GetCodesWithFilterUseCase(repository = get())
    }

    factory<RecognizeCodeUseCase> {
        RecognizeCodeInteractor(
            scanner = BarcodeScanning.getClient()
        )
    }
}