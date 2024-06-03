package com.t_ovchinnikova.android.scandroid_2.scanner_impl.di

import androidx.camera.core.ImageAnalysis
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_executor.CoroutineDispatcherProvider
import com.t_ovchinnikova.android.scandroid_2.scanner_api.usecases.CropImageUseCase
import com.t_ovchinnikova.android.scandroid_2.scanner_api.usecases.RecognizeCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.ScanAnalyzer
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.datasource.ScannerDataSource
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.datasource.ScannerDataSourceImpl
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.interactors.CropImageInteractor
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.interactors.GetScannedCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.interactors.GetScannedCodeUseCaseImpl
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.interactors.RecognizeCodeInteractor
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.interactors.SetScannedCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.interactors.SetScannedCodeUseCaseImpl
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.mappers.MlKitFormatToCodeFormatMapper
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.mappers.MlKitTypeToCodeTypeMapper
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.repository.ScannerRepository
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.repository.ScannerRepositoryImpl
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.viewmodel.ScanningViewModel
import com.t_ovchinnikova.android.scandroid_2.settings_api.usecases.GetSettingsUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val scannerModule = module {

    factory<ImageAnalysis.Analyzer> { (heightCropPercent: Int, widthCropPercent: Int) ->
        ScanAnalyzer(
            recognizeCodeUseCase = get() as RecognizeCodeUseCase,
            cropImageUseCase = get() as CropImageUseCase,
            heightCropPercent = heightCropPercent,
            widthCropPercent = widthCropPercent
        )
    }

    factory<RecognizeCodeUseCase> {
        RecognizeCodeInteractor(
            toCodeTypeMapper = MlKitTypeToCodeTypeMapper,
            toCodeFormatMapper = MlKitFormatToCodeFormatMapper,
            scanner = BarcodeScanning.getClient(),
            setScannedCodeUseCase = get() as SetScannedCodeUseCase
        )
    }

    factory<CropImageUseCase> {
        CropImageInteractor()
    }

    factory<SetScannedCodeUseCase> {
        SetScannedCodeUseCaseImpl(
            repository = get() as ScannerRepository
        )
    }

    factory<GetScannedCodeUseCase> {
        GetScannedCodeUseCaseImpl(
            repository = get() as ScannerRepository
        )
    }

    single<MlKitTypeToCodeTypeMapper> {
        MlKitTypeToCodeTypeMapper
    }

    single<MlKitFormatToCodeFormatMapper> {
        MlKitFormatToCodeFormatMapper
    }

    single<ScannerDataSource> {
        ScannerDataSourceImpl()
    }

    factory<ScannerRepository> {
        ScannerRepositoryImpl(
            dataSource = get() as ScannerDataSource
        )
    }

    viewModel<ScanningViewModel> {
        ScanningViewModel(
            addCodeUseCase = get() as AddCodeUseCase,
            getSettingsUseCase = get() as GetSettingsUseCase,
            getScannedCodeUseCase = get() as GetScannedCodeUseCase,
            dispatcher = (get() as CoroutineDispatcherProvider).io
        )
    }
}