package com.t_ovchinnikova.android.scandroid_2.di

import com.t_ovchinnikova.android.scandroid_2.data.repository.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.data.repository.SettingsRepository
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

    factory<SaveSettingsUseCase> {
        SaveSettingsInteractor(
            settingsRepository = get() as SettingsRepository
        )
    }

    factory<GetSettingsUseCase> {
        GetSettingsInteractor(
            settingsRepository = get() as SettingsRepository
        )
    }

    factory<GetCodeUseCase> {
        GetCodeInteractor(
            repository = get() as CodeRepository
        )
    }
}