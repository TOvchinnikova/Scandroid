package com.t_ovchinnikova.android.scandroid_2.ui

import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.navigation.GRAPH_HISTORY
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.navigation.GRAPH_SCANNER
import com.t_ovchinnikova.android.scandroid_2.settings_impl.navigation.ROUTE_SETTINGS

sealed class NavigationItem(
    val route: String,
    val titleResId: Int,
    val iconResId: Int
) {

    object Scanner : NavigationItem(
        route = GRAPH_SCANNER,
        titleResId = R.string.scanner,
        iconResId = R.drawable.ic_qr_code
    )

    object History : NavigationItem(
        route = GRAPH_HISTORY,
        titleResId = R.string.history,
        iconResId = R.drawable.ic_history
    )

    object Settings : NavigationItem(
        route = ROUTE_SETTINGS,
        titleResId = R.string.settings,
        iconResId = R.drawable.ic_settings
    )

}
