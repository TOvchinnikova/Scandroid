package com.t_ovchinnikova.android.scandroid_2.data

import com.google.mlkit.vision.barcode.common.Barcode.*
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code

fun Code.formatToStringId(): Int {
    return when(this.format) {
        FORMAT_AZTEC -> R.string.barcode_format_aztec
        FORMAT_CODE_128 -> R.string.barcode_format_code_128
        FORMAT_CODABAR -> R.string.barcode_format_codabar
        FORMAT_CODE_39 -> R.string.barcode_format_code_39
        FORMAT_CODE_93 -> R.string.barcode_format_code_93
        FORMAT_DATA_MATRIX -> R.string.barcode_format_data_matrix
        FORMAT_EAN_8 -> R.string.barcode_format_ean_8
        FORMAT_EAN_13 -> R.string.barcode_format_ean_13
        FORMAT_ITF -> R.string.barcode_format_itf
        FORMAT_PDF417 -> R.string.barcode_format_pdf_417
        FORMAT_QR_CODE -> R.string.barcode_format_qr_code
        FORMAT_UPC_A -> R.string.barcode_format_UPC_A
        FORMAT_UPC_E -> R.string.barcode_format_UPC_E
        else -> R.string.barcode_format_unknown
    }
}

fun Code.typeToString(): Int {
    return when(this.type) {
        TYPE_CONTACT_INFO -> R.string.barcode_type_contact_info
        TYPE_EMAIL -> R.string.barcode_type_email
        TYPE_ISBN -> R.string.barcode_type_isbn
        TYPE_PHONE -> R.string.barcode_type_phone
        TYPE_PRODUCT -> R.string.barcode_type_product
        TYPE_SMS -> R.string.barcode_type_sms
        TYPE_URL -> R.string.barcode_type_url
        TYPE_WIFI -> R.string.barcode_type_wifi
        TYPE_GEO -> R.string.barcode_type_geo
        TYPE_CALENDAR_EVENT -> R.string.barcode_type_calendar_event
        TYPE_DRIVER_LICENSE -> R.string.barcode_driver_license
        else -> R.string.barcode_type_text
    }
}

fun Code.formatToImageId(): Int {
    return when(this.format) {
        FORMAT_QR_CODE -> R.drawable.ic_qr_code
        FORMAT_DATA_MATRIX -> R.drawable.ic_data_matrix
        FORMAT_AZTEC -> R.drawable.ic_aztec
        else -> R.drawable.ic_barcode
    }
}