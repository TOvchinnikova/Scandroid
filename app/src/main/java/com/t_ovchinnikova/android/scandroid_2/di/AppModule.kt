package com.t_ovchinnikova.android.scandroid_2.di

import android.content.Context
import com.t_ovchinnikova.android.scandroid_2.Settings
import com.t_ovchinnikova.android.scandroid_2.presentation.HistoryViewModel
import com.t_ovchinnikova.android.scandroid_2.presentation.ScanResultViewModel
import com.t_ovchinnikova.android.scandroid_2.presentation.ScanningViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<HistoryViewModel> {
        HistoryViewModel(
            deleteAllCodesUseCase = get(),
            deleteCodeUseCase = get(),
            getCodesUseCase = get()
        )
    }

    viewModel<ScanningViewModel> {
        ScanningViewModel(
            addCodeUseCase = get()
        )
    }

    viewModel<ScanResultViewModel> {
        ScanResultViewModel(
            deleteCodeUseCase = get(),
            addCodeUseCase = get()
        )
    }

    single<Settings> {
        Settings(
            sharedPreferences = androidContext().getSharedPreferences(
                Settings.SHARED_PREFERENCES_SETTINGS,
                Context.MODE_PRIVATE
            )
        )
    }
}