package com.t_ovchinnikova.android.scandroid_2.scanner_impl.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.presentation.ui.ScannerScreen
import java.util.UUID

const val ROUTE_SCANNER = "scanner"
const val GRAPH_SCANNER = "scanner_graph"

fun NavController.navigateToScannerGraph(navOptions: NavOptions? = null) {
    this.navigate(GRAPH_SCANNER, navOptions)
}

fun NavGraphBuilder.scannerGraph(
    paddingValues: PaddingValues,
    onScanListener: (codeId: UUID) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = GRAPH_SCANNER,
        startDestination = ROUTE_SCANNER
    ) {
        composable(route = ROUTE_SCANNER) {
            ScannerScreen(
                paddingValues = paddingValues,
                onScanListener = onScanListener
            )
        }
        nestedGraphs()
    }
}