package com.t_ovchinnikova.android.scandroid_2.di

import androidx.camera.core.ImageAnalysis
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.*
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors.RecognizeCodeInteractor
import com.t_ovchinnikova.android.scandroid_2.presentation.ScanAnalyzer
import com.t_ovchinnikova.android.scandroid_2.presentation.ScanResultListener
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.HistoryViewModel
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.ScanResultViewModel
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.ScanningViewModel
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<HistoryViewModel> {
        HistoryViewModel(
            deleteAllCodesUseCase = get() as DeleteAllCodesUseCase,
            deleteCodeUseCase = get() as DeleteCodeUseCase,
            getCodesUseCase = get() as GetCodesUseCase
        )
    }

    viewModel<ScanningViewModel> {
        ScanningViewModel(
            addCodeUseCase = get() as AddCodeUseCase,
            getSettingsUseCase = get() as GetSettingsUseCase
        )
    }

    viewModel<ScanResultViewModel> {
        ScanResultViewModel(
            deleteCodeUseCase = get() as DeleteCodeUseCase,
            addCodeUseCase = get() as AddCodeUseCase,
            getSettingsUseCase = get() as GetSettingsUseCase
        )
    }

    viewModel<SettingsViewModel> {
        SettingsViewModel(
            saveSettingsUseCase = get() as SaveSettingsUseCase,
            getSettingsUseCase = get() as GetSettingsUseCase
        )
    }

    factory<ImageAnalysis.Analyzer> { (scanResultListener: ScanResultListener) ->
        ScanAnalyzer(
            recognizeCodeUseCase = get() as RecognizeCodeUseCase,
            listener = scanResultListener
        )
    }

    factory<RecognizeCodeUseCase> {
        RecognizeCodeInteractor()
    }
}