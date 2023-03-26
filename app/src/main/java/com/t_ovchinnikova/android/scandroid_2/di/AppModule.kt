package com.t_ovchinnikova.android.scandroid_2.di

import androidx.camera.core.ImageAnalysis
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.*
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors.CropImageInteractor
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors.RecognizeCodeInteractor
import com.t_ovchinnikova.android.scandroid_2.presentation.ScanAnalyzer
import com.t_ovchinnikova.android.scandroid_2.presentation.ScanResultListener
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.HistoryViewModel
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.CodeDetailsViewModel
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.ScanningViewModel
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.UUID

val appModule = module {

    viewModel<HistoryViewModel> {
        HistoryViewModel(
            deleteAllCodesUseCase = get() as com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.DeleteAllCodesUseCase,
            deleteCodeUseCase = get() as com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.DeleteCodeUseCase,
            getCodesUseCase = get() as com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.GetCodesUseCase,
            addCodeUseCase = get() as com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.AddCodeUseCase
        )
    }

    viewModel<ScanningViewModel> {
        ScanningViewModel(
            addCodeUseCase = get() as com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.AddCodeUseCase,
            getSettingsUseCase = get() as com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.GetSettingsUseCase
        )
    }

    viewModel<CodeDetailsViewModel> { (codeId: UUID) ->
        CodeDetailsViewModel(
            codeId = codeId,
            deleteCodeUseCase = get() as com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.DeleteCodeUseCase,
            addCodeUseCase = get() as com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.AddCodeUseCase,
            getCodeUseCase = get() as com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.GetCodeUseCase,
            getSettingsUseCase = get() as com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.GetSettingsUseCase
        )
    }

    viewModel<SettingsViewModel> {
        SettingsViewModel(
            saveSettingsUseCase = get() as com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.SaveSettingsUseCase,
            getSettingsUseCase = get() as com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.GetSettingsUseCase
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