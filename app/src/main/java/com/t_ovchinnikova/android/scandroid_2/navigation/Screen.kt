package com.t_ovchinnikova.android.scandroid_2.navigation

import java.util.UUID

sealed class Screen(
    val route: String
) {
    object ScannerMain : Screen(ROUTE_SCANNER_MAIN)
    object Scanner : Screen(ROUTE_SCANNER)
    object CodeDetails : Screen(ROUTE_CODE_DETAILS) {

        private const val ROUTE_FOR_ARGS = "code_details"

        fun getRouteWithArgs(codeId: UUID): String {
            return "$ROUTE_FOR_ARGS/$codeId"
        }
    }
    object HistoryCodeDetails : Screen(ROUTE_CODE_INFO_FROM_HISTORY) {

        private const val ROUTE_FOR_ARGS = "history_code_details"

        fun getRouteWithArgs(codeId: UUID): String {
            return "$ROUTE_FOR_ARGS/$codeId"
        }
    }
    object HistoryMain : Screen(ROUTE_HISTORY_MAIN)
    object History : Screen(ROUTE_HISTORY)
    object Settings : Screen(ROUTE_SETTINGS)

    companion object {
        const val KEY_CODE_ID = "code_id"

        const val ROUTE_SCANNER_MAIN = "main"
        const val ROUTE_SCANNER = "scanner"
        const val ROUTE_CODE_DETAILS = "code_details/{$KEY_CODE_ID}"
        const val ROUTE_CODE_INFO_FROM_HISTORY = "history_code_details/{$KEY_CODE_ID}"
        const val ROUTE_HISTORY = "history"
        const val ROUTE_HISTORY_MAIN = "history_main"
        const val ROUTE_SETTINGS = "settings"
    }
}