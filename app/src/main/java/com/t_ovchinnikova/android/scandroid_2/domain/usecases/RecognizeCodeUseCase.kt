package com.t_ovchinnikova.android.scandroid_2.domain.usecases

import com.google.mlkit.vision.common.InputImage
import com.t_ovchinnikova.android.scandroid_2.presentation.ScanResultListener

interface RecognizeCodeUseCase {

    operator fun invoke(image: InputImage, listener: ScanResultListener)
}