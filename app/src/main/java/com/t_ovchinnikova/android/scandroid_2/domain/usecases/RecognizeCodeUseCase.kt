package com.t_ovchinnikova.android.scandroid_2.domain.usecases

import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

interface RecognizeCodeUseCase {

    operator fun invoke(image: InputImage): Task<List<Barcode>>
}