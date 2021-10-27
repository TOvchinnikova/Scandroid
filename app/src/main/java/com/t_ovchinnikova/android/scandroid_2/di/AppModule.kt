package com.t_ovchinnikova.android.scandroid_2.di

import com.t_ovchinnikova.android.scandroid_2.presentation.HistoryViewModel
import com.t_ovchinnikova.android.scandroid_2.presentation.ScanResultViewModel
import com.t_ovchinnikova.android.scandroid_2.presentation.ScanningViewModel
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
}