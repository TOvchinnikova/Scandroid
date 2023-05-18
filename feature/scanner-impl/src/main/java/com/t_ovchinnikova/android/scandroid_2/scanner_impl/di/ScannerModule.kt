package com.t_ovchinnikova.android.scandroid_2.scanner_impl.di

import androidx.camera.core.ImageAnalysis
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.GetSettingsUseCase
import com.t_ovchinnikova.android.scandroid_2.scanner_api.ScanResultListener
import com.t_ovchinnikova.android.scandroid_2.scanner_api.usecases.CropImageUseCase
import com.t_ovchinnikova.android.scandroid_2.scanner_api.usecases.RecognizeCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.ScanAnalyzer
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.interactors.CropImageInteractor
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.interactors.RecognizeCodeInteractor
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.mappers.MlKitFormatToCodeFormatMapper
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.mappers.MlKitTypeToCodeTypeMapper
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.viewmodel.ScanningViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val scannerModule = module {

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
        RecognizeCodeInteractor(
            toCodeTypeMapper = MlKitTypeToCodeTypeMapper,
            toCodeFormatMapper = MlKitFormatToCodeFormatMapper
        )
    }

    factory<CropImageUseCase> {
        CropImageInteractor()
    }

    single<MlKitTypeToCodeTypeMapper> {
        MlKitTypeToCodeTypeMapper
    }

    single<MlKitFormatToCodeFormatMapper> {
        MlKitFormatToCodeFormatMapper
    }

    viewModel<ScanningViewModel> {
        ScanningViewModel(
            addCodeUseCase = get() as AddCodeUseCase,
            getSettingsUseCase = get() as GetSettingsUseCase
        )
    }
}