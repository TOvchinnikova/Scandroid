package com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors

import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.RecognizeCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.presentation.ScanResultListener
import java.util.*

class RecognizeCodeInteractor : RecognizeCodeUseCase {

    override fun invoke(image: InputImage, listener: ScanResultListener) {
        val scanner = BarcodeScanning.getClient()
        scanner.process(image).addOnSuccessListener {
            checkList(it)?.let {
                listener.onScanned(it)
            }
        }
            .addOnCompleteListener {
                scanner.close()
            }
    }

    private fun checkList(list: List<Barcode>): Code? {
        return list.firstOrNull().let { barcode ->
            val rawValue = barcode?.rawValue
            rawValue?.let {
                val format = barcode.format
                val type = barcode.valueType
                Code(
                    id = UUID.randomUUID(),
                    text = rawValue,
                    format = format,
                    type = type
                )
            }
        }
    }
}