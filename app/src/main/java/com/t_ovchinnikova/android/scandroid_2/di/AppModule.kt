package com.t_ovchinnikova.android.scandroid_2.di

import android.content.Context
import androidx.camera.core.ImageAnalysis
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.t_ovchinnikova.android.scandroid_2.Settings
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.RecognizeCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors.RecognizeCodeInteractor
import com.t_ovchinnikova.android.scandroid_2.presentation.ScanAnalyzer
import com.t_ovchinnikova.android.scandroid_2.presentation.ScanResultListener
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.HistoryViewModel
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.ScanResultViewModel
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.ScanningViewModel
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

    factory<ImageAnalysis.Analyzer> { (scanResultListener: ScanResultListener) ->
        ScanAnalyzer(
            listener = scanResultListener,
            recognizeCodeUseCase = get() as RecognizeCodeUseCase
        )
    }

    factory<RecognizeCodeUseCase> {
        RecognizeCodeInteractor(
            scanner = BarcodeScanning.getClient()
        )
    }
}