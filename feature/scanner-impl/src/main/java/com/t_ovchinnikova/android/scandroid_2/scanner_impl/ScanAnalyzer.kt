package com.t_ovchinnikova.android.scandroid_2.scanner_impl

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.t_ovchinnikova.android.scandroid_2.scanner_api.usecases.CropImageUseCase
import com.t_ovchinnikova.android.scandroid_2.scanner_api.usecases.RecognizeCodeUseCase

class ScanAnalyzer(
    private val recognizeCodeUseCase: RecognizeCodeUseCase,
    private val cropImageUseCase: CropImageUseCase,
    private val heightCropPercent: Int,
    private val widthCropPercent: Int
) : ImageAnalysis.Analyzer {

    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            cropImageUseCase.invoke(
                imageProxy,
                mediaImage,
                heightCropPercent,
                widthCropPercent
            )?.let { croppedBitmap ->
                recognizeCodeUseCase(InputImage.fromBitmap(croppedBitmap, 0))
                imageProxy.close()
            }
        }
    }
}