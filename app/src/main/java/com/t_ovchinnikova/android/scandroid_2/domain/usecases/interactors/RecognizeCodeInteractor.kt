package com.t_ovchinnikova.android.scandroid_2.domain.usecases.interactors

import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.data.MlKitFormatToCodeFormatMapper
import com.t_ovchinnikova.android.scandroid_2.data.MlKitTypeToCodeTypeMapper
import com.t_ovchinnikova.android.scandroid_2.domain.usecases.RecognizeCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.presentation.ScanResultListener
import java.util.*

class RecognizeCodeInteractor(
    private val toCodeFormatMapper: MlKitFormatToCodeFormatMapper,
    private val toCodeTypeMapper: MlKitTypeToCodeTypeMapper
) : RecognizeCodeUseCase {

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
                Code(
                    id = UUID.randomUUID(),
                    text = rawValue,
                    format = toCodeFormatMapper.map(barcode.format),
                    type = toCodeTypeMapper.map(barcode.valueType)
                )
            }
        }
    }
}