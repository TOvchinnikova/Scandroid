package com.t_ovchinnikova.android.scandroid_2.scanner_impl.mappers

import com.google.mlkit.vision.barcode.common.Barcode
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeType

object MlKitTypeToCodeTypeMapper {

    fun map(typeId: Int): CodeType {
        return when (typeId) {
            Barcode.TYPE_TEXT -> CodeType.TEXT
            Barcode.TYPE_CONTACT_INFO -> CodeType.CONTACT_INFO
            Barcode.TYPE_EMAIL -> CodeType.EMAIL
            Barcode.TYPE_ISBN -> CodeType.ISBN
            Barcode.TYPE_PHONE -> CodeType.PHONE
            Barcode.TYPE_PRODUCT -> CodeType.PRODUCT
            Barcode.TYPE_SMS -> CodeType.SMS
            Barcode.TYPE_URL -> CodeType.URL
            Barcode.TYPE_WIFI -> CodeType.WIFI
            Barcode.TYPE_GEO -> CodeType.GEO
            Barcode.TYPE_CALENDAR_EVENT -> CodeType.CALENDAR_EVENT
            Barcode.TYPE_DRIVER_LICENSE -> CodeType.DRIVER_LICENSE
            else -> CodeType.UNKNOWN
        }
    }
}