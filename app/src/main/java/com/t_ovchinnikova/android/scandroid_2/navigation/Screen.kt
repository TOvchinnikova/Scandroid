package com.t_ovchinnikova.android.scandroid_2.navigation

sealed class Screen(
    val route: String
) {
    object Main : Screen(ROUTE_MAIN)
    object Scanner : Screen(ROUTE_SCANNER)
    object CodeInfo : Screen(ROUTE_CODE_INFO)
    object History : Screen(ROUTE_HISTORY)
    object Settings : Screen(ROUTE_SETTINGS)

    companion object {
        const val ROUTE_MAIN = "main"
        const val ROUTE_SCANNER = "scanner"
        const val ROUTE_CODE_INFO = "code_info"
        const val ROUTE_HISTORY = "history"
        const val ROUTE_SETTINGS = "settings"
    }
}