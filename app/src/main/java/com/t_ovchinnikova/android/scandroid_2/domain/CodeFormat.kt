package com.t_ovchinnikova.android.scandroid_2.domain

enum class CodeFormat(private val tag: Int) {
    AZTEC(4096),
    CODE_128(1),
    CODABAR(8),
    CODE_39(2),
    CODE_93(4),
    DATA_MATRIX(16),
    EAN_8(64),
    EAN_13(32),
    ITF(128),
    PDF417(2048),
    QR_CODE(256),
    UPC_A(512),
    UPC_E(1024),
    UNKNOWN(-1)
}