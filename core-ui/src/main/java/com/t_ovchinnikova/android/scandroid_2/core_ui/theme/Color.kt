package com.t_ovchinnikova.android.scandroid_2.core_ui.theme

import androidx.compose.ui.graphics.Color

val ColorPrimary = Color(0xFF018786)
val ColorSecondary = Color(0xFF686565)
val ColorScannerButtonPanel = Color(0xFF686565)
val DarkColorBackground = Color(0xFF212121)
val DarkColorNavigationBackground = Color(0xFF252525)
val SearchFieldBackgroundColor = Color(0xFFDADDDC)
val UncheckedSwitchBackgroundColor = Color(0xFFDADDDC)
val ColorNavigationBackground = Color(0xFFF8F6F6)

fun getNavigationBottomBackgroundColor(isDarkTheme: Boolean): Color = if (isDarkTheme) {
    DarkColorNavigationBackground
} else {
    ColorNavigationBackground
}