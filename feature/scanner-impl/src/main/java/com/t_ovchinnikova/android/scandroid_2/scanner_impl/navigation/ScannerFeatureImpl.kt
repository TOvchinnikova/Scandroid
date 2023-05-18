package com.t_ovchinnikova.android.scandroid_2.scanner_impl.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.t_ovchinnikova.android.scandroid_2.scanner_api.ScannerFeature
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.ui.ScannerScreen
import java.util.UUID

class ScannerFeatureImpl : ScannerFeature {

    override val route: String
        get() = ROUTE_SCANNER

    override fun navigateToScannerGraph(navController: NavController) {
        navController.navigate(route)
    }

    override fun scannerGraph(
        navGraphBuilder: NavGraphBuilder,
        paddingValues: PaddingValues,
        onScanListener: (codeId: UUID) -> Unit,
        nestedGraphs: NavGraphBuilder.() -> Unit
    ) {
        navGraphBuilder.navigation(
            route = GRAPH_SCANNER,
            startDestination = route
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

    companion object {
        const val ROUTE_SCANNER = "scanner"
        const val GRAPH_SCANNER = "scanner_graph"
    }
}