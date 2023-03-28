package com.t_ovchinnikova.android.scandroid_2.core_domain.entity

enum class CodeType(val tag: Int) {
    CONTACT_INFO(1),
    EMAIL(2),
    ISBN(3),
    PHONE(4),
    PRODUCT(5),
    SMS(6),
    TEXT(7),
    URL(8),
    WIFI(9),
    GEO(10),
    CALENDAR_EVENT(11),
    DRIVER_LICENSE(12),
    UNKNOWN(0)
}