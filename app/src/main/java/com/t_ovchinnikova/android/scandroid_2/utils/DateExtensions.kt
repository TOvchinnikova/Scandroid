package com.t_ovchinnikova.android.scandroid_2.utils

import java.text.DateFormat
import java.util.*

fun Date.toStringByPattern(formatter: DateFormat): String =
    formatter.format(this)