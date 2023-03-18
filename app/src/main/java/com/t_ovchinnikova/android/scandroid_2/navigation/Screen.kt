package com.t_ovchinnikova.android.scandroid_2.navigation

import java.util.UUID

sealed class Screen(
    val route: String
) {
    object ScannerMain : Screen(ROUTE_MAIN)
    object Scanner : Screen(ROUTE_SCANNER)
    object CodeInfo : Screen(ROUTE_CODE_INFO) {

        private const val ROUTE_FOR_ARGS = "code_info"

        fun getRouteWithArgs(codeId: UUID): String {
            return "$ROUTE_FOR_ARGS/$codeId"
        }
    }
    object HistoryMain : Screen(ROUTE_HISTORY_MAIN)
    object History : Screen(ROUTE_HISTORY)
    object Settings : Screen(ROUTE_SETTINGS)

    companion object {
        const val KEY_CODE_ID = "code_id"

        const val ROUTE_MAIN = "main"
        const val ROUTE_SCANNER = "scanner"
        const val ROUTE_CODE_INFO = "code_info/{$KEY_CODE_ID}"
        const val ROUTE_HISTORY = "history"
        const val ROUTE_HISTORY_MAIN = "history_main"
        const val ROUTE_SETTINGS = "settings"
    }
}