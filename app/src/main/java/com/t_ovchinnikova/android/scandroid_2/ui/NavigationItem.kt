package com.t_ovchinnikova.android.scandroid_2.ui

import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.navigation.Screen
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.navigation.GRAPH_SCANNER

sealed class NavigationItem(
    val screen: Screen,
    val titleResId: Int,
    val iconResId: Int
) {

    object Scanner : NavigationItem(
        screen = Screen.ScannerMain,
        titleResId = R.string.scanner,
        iconResId = R.drawable.ic_qr_code
    )

    object History : NavigationItem(
        screen = Screen.HistoryMain,
        titleResId = R.string.history,
        iconResId = R.drawable.ic_history
    )

    object Settings : NavigationItem(
        screen = Screen.Settings,
        titleResId = R.string.settings,
        iconResId = R.drawable.ic_settings
    )

}
