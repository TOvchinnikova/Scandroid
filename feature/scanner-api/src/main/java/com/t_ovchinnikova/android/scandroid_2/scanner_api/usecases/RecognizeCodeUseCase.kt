package com.t_ovchinnikova.android.scandroid_2.scanner_api.usecases

import com.google.mlkit.vision.common.InputImage

interface RecognizeCodeUseCase {

    operator fun invoke(image: InputImage)
}