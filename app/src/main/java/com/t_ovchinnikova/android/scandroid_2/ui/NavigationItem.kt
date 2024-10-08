package com.t_ovchinnikova.android.scandroid_2.ui

import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.navigation.GRAPH_HISTORY
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.presentation.navigation.GRAPH_SCANNER
import com.t_ovchinnikova.android.scandroid_2.settings_impl.navigation.ROUTE_SETTINGS
import com.t_ovchinnikova.android.scandroid_2.core_resources.R as CoreResources

sealed class NavigationItem(
    val route: String,
    val titleResId: Int,
    val iconResId: Int
) {

    data object Scanner : NavigationItem(
        route = GRAPH_SCANNER,
        titleResId = R.string.scanner,
        iconResId = CoreResources.drawable.ic_qr_code
    )

    data object History : NavigationItem(
        route = GRAPH_HISTORY,
        titleResId = CoreResources.string.history,
        iconResId = R.drawable.ic_history
    )

    data object Settings : NavigationItem(
        route = ROUTE_SETTINGS,
        titleResId = CoreResources.string.settings,
        iconResId = R.drawable.ic_settings
    )

}
