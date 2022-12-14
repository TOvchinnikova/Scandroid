package com.t_ovchinnikova.android.scandroid_2.ui

sealed class Screen(
    val route: String
) {

    object Scanner : Screen(ROUTE_SCANNER)
    object History : Screen(ROUTE_HISTORY)
    object Settings : Screen(ROUTE_SETTINGS)

    companion object {
        const val ROUTE_SCANNER = "scanner"
        const val ROUTE_HISTORY = "history"
        const val ROUTE_SETTINGS = "settings"
    }
}