package com.t_ovchinnikova.android.scandroid_2.data

import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeFormat
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeType

fun CodeFormat.toStringRes(): Int {
    return when(this) {
        CodeFormat.AZTEC -> R.string.barcode_format_aztec
        CodeFormat.CODE_128 -> R.string.barcode_format_code_128
        CodeFormat.CODABAR -> R.string.barcode_format_codabar
        CodeFormat.CODE_39 -> R.string.barcode_format_code_39
        CodeFormat.CODE_93 -> R.string.barcode_format_code_93
        CodeFormat.DATA_MATRIX -> R.string.barcode_format_data_matrix
        CodeFormat.EAN_8 -> R.string.barcode_format_ean_8
        CodeFormat.EAN_13 -> R.string.barcode_format_ean_13
        CodeFormat.ITF -> R.string.barcode_format_itf
        CodeFormat.PDF417 -> R.string.barcode_format_pdf_417
        CodeFormat.QR_CODE -> R.string.barcode_format_qr_code
        CodeFormat.UPC_A -> R.string.barcode_format_UPC_A
        CodeFormat.UPC_E -> R.string.barcode_format_UPC_E
        CodeFormat.UNKNOWN -> R.string.barcode_format_unknown
    }
}

fun CodeType.toStringRes(): Int {
    return when(this) {
        CodeType.CONTACT_INFO -> R.string.barcode_type_contact_info
        CodeType.EMAIL -> R.string.barcode_type_email
        CodeType.ISBN -> R.string.barcode_type_isbn
        CodeType.PHONE -> R.string.barcode_type_phone
        CodeType.PRODUCT -> R.string.barcode_type_product
        CodeType.SMS -> R.string.barcode_type_sms
        CodeType.URL -> R.string.barcode_type_url
        CodeType.WIFI -> R.string.barcode_type_wifi
        CodeType.GEO -> R.string.barcode_type_geo
        CodeType.CALENDAR_EVENT -> R.string.barcode_type_calendar_event
        CodeType.DRIVER_LICENSE -> R.string.barcode_driver_license
        CodeType.UNKNOWN, CodeType.TEXT -> R.string.barcode_type_text
    }
}

fun CodeFormat.toImageId(): Int {
    return when(this) {
        CodeFormat.QR_CODE -> R.drawable.ic_qr_code
        CodeFormat.DATA_MATRIX -> R.drawable.ic_data_matrix
        CodeFormat.AZTEC -> R.drawable.ic_aztec
        else -> R.drawable.ic_barcode
    }
}