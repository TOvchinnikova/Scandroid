package com.t_ovchinnikova.android.scandroid_2.scanner_api

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import java.util.UUID

interface ScannerFeature {

    val route: String

    fun navigateToScannerGraph(
        navController: NavController
    )

    fun scannerGraph(
        navGraphBuilder: NavGraphBuilder,
        paddingValues: PaddingValues,
        onScanListener: (codeId: UUID) -> Unit,
        nestedGraphs: NavGraphBuilder.() -> Unit
    )
}