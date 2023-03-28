package com.t_ovchinnikova.android.scandroid_2.data

import com.google.mlkit.vision.barcode.common.Barcode
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeFormat

object MlKitFormatToCodeFormatMapper {

    fun map(formatId: Int): CodeFormat {
        return when (formatId) {
            Barcode.FORMAT_QR_CODE -> CodeFormat.QR_CODE
            Barcode.FORMAT_AZTEC -> CodeFormat.AZTEC
            Barcode.FORMAT_DATA_MATRIX -> CodeFormat.DATA_MATRIX
            Barcode.FORMAT_CODE_128 -> CodeFormat.CODE_128
            Barcode.FORMAT_CODE_93 -> CodeFormat.CODE_93
            Barcode.FORMAT_CODABAR -> CodeFormat.CODABAR
            Barcode.FORMAT_EAN_13 -> CodeFormat.EAN_13
            Barcode.FORMAT_EAN_8 -> CodeFormat.EAN_8
            Barcode.FORMAT_CODE_39 -> CodeFormat.CODE_39
            Barcode.FORMAT_ITF -> CodeFormat.ITF
            Barcode.FORMAT_PDF417 -> CodeFormat.PDF417
            Barcode.FORMAT_UPC_A -> CodeFormat.UPC_A
            Barcode.FORMAT_UPC_E -> CodeFormat.UPC_E
            else -> CodeFormat.UNKNOWN
        }
    }
}