package com.t_ovchinnikova.android.scandroid_2.core_platform_utils

import java.text.DateFormat
import java.util.*

fun Date.toStringByPattern(formatter: DateFormat): String =
    formatter.format(this)