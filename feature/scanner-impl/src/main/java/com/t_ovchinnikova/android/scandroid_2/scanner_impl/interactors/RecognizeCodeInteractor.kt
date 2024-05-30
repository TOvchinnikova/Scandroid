package com.t_ovchinnikova.android.scandroid_2.scanner_impl.interactors

import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.scanner_api.ScanResultListener
import com.t_ovchinnikova.android.scandroid_2.scanner_api.usecases.RecognizeCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.mappers.MlKitFormatToCodeFormatMapper
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.mappers.MlKitTypeToCodeTypeMapper
import java.util.UUID

class RecognizeCodeInteractor(
    private val toCodeFormatMapper: MlKitFormatToCodeFormatMapper,
    private val toCodeTypeMapper: MlKitTypeToCodeTypeMapper,
    private val scanner: BarcodeScanner
) : RecognizeCodeUseCase {

    override fun invoke(image: InputImage, listener: ScanResultListener) {
        scanner.process(image).addOnSuccessListener {
            checkList(it)?.let {
                listener.onScanned(it)
            }
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