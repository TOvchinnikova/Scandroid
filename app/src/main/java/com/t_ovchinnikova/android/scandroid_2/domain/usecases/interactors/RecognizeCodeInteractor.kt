package com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors

import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.RecognizeCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.presentation.ScanResultListener

class RecognizeCodeInteractor(
    private val scanner: BarcodeScanner//,
    //private val listener: ScanResultListener
) : RecognizeCodeUseCase {

    override fun invoke(image: InputImage, listener: ScanResultListener) {
        scanner.process(image).addOnSuccessListener {
            checkList(it)?.let {
                listener.onScanned(it)
                scanner.close()
            }
        }
    }

    private fun checkList(list: List<Barcode>): Code? {
        return list.firstOrNull().let { barcode ->
            val rawValue = barcode?.rawValue
            rawValue?.let {
                val format = barcode.format
                val type = barcode.valueType
                //val code =
                    Code(text = rawValue, format = format, type = type)
                //listener.onScanned(code)
            }
        }
    }
}