package com.t_ovchinnikova.android.scandroid_2.di

import androidx.camera.core.ImageAnalysis
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.*
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors.CropImageInteractor
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors.RecognizeCodeInteractor
import com.t_ovchinnikova.android.scandroid_2.presentation.ScanAnalyzer
import com.t_ovchinnikova.android.scandroid_2.presentation.ScanResultListener
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.HistoryViewModel
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.CodeInfoViewModel
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.ScanningViewModel
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.UUID

val appModule = module {

    viewModel<HistoryViewModel> {
        HistoryViewModel(
            deleteAllCodesUseCase = get() as DeleteAllCodesUseCase,
            deleteCodeUseCase = get() as DeleteCodeUseCase,
            getCodesUseCase = get() as GetCodesUseCase,
            addCodeUseCase = get() as AddCodeUseCase
        )
    }

    viewModel<ScanningViewModel> {
        ScanningViewModel(
            addCodeUseCase = get() as AddCodeUseCase,
            getSettingsUseCase = get() as GetSettingsUseCase
        )
    }

    viewModel<CodeInfoViewModel> { (codeId: UUID) ->
        CodeInfoViewModel(
            codeId = codeId,
            deleteCodeUseCase = get() as DeleteCodeUseCase,
            addCodeUseCase = get() as AddCodeUseCase,
            getCodeUseCase = get() as GetCodeUseCase,
            getSettingsUseCase = get() as GetSettingsUseCase
        )
    }

    viewModel<SettingsViewModel> {
        SettingsViewModel(
            saveSettingsUseCase = get() as SaveSettingsUseCase,
            getSettingsUseCase = get() as GetSettingsUseCase
        )
    }

    factory<ImageAnalysis.Analyzer> { (scanResultListener: ScanResultListener, heightCropPercent: Int, widthCropPercent: Int) ->
        ScanAnalyzer(
            recognizeCodeUseCase = get() as RecognizeCodeUseCase,
            listener = scanResultListener,
            cropImageUseCase = get() as CropImageUseCase,
            heightCropPercent = heightCropPercent,
            widthCropPercent = widthCropPercent
        )
    }

    factory<RecognizeCodeUseCase> {
        RecognizeCodeInteractor()
    }

    factory<CropImageUseCase> {
        CropImageInteractor()
    }
}