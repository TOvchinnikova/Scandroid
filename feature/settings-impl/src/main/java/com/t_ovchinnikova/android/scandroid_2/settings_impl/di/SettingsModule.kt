package com.t_ovchinnikova.android.scandroid_2.settings_impl.di

import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.GetSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.SaveSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.settings_impl.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {

    viewModel<SettingsViewModel> {
        SettingsViewModel(
            saveSettingsUseCase = get() as SaveSettingsUseCase,
            getSettingsUseCase = get() as GetSettingsUseCase
        )
    }
}