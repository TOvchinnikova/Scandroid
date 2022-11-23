package com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors

import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.RecognizeCodeUseCase

class RecognizeCodeInteractor(
    private val scanner: BarcodeScanner
) : RecognizeCodeUseCase {

    override fun invoke(image: InputImage): Task<List<Barcode>> {
        return scanner.process(image)
    }
}