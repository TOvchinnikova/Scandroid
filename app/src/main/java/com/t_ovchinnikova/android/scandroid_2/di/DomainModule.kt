package com.t_ovchinnikova.android.scandroid_2.di

import com.t_ovchinnikova.android.scandroid_2.domain.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.*
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors.*
import org.koin.dsl.module

val domainModule = module {

    factory<AddCodeUseCase> {
        AddCodeInteractor(
            repository = get() as CodeRepository
        )
    }

    factory<DeleteAllCodesUseCase> {
        DeleteAllCodesInteractor(
            repository = get() as CodeRepository
        )
    }

    factory<DeleteCodeUseCase> {
        DeleteCodeInteractor(
            repository = get() as CodeRepository
        )
    }

    factory<GetCodesUseCase> {
        GetCodesInteractor(
            repository = get() as CodeRepository
        )
    }

    factory<GetCodesWithFilterUseCase> {
        GetCodesWithFilterInteractor(
            repository = get() as CodeRepository
        )
    }
}