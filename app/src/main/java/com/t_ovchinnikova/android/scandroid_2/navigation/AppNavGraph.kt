package com.t_ovchinnikova.android.scandroid_2.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.navigation.codeDetailsScreen
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.navigation.navigateToCodeDetails
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.navigation.codeListGraph
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.navigation.GRAPH_SCANNER
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.navigation.scannerGraph
import com.t_ovchinnikova.android.scandroid_2.settings_impl.navigation.settingsScreen

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
) {
    NavHost(
        navController = navHostController,
        startDestination = GRAPH_SCANNER
    ) {
        scannerGraph(
            paddingValues = paddingValues,
            onScanListener = { codeUuid ->
                navHostController.navigateToCodeDetails(
                    codeUuid = codeUuid
                )
            },
            nestedGraphs = {
                codeDetailsScreen(
                    onBackPressed = navHostController::popBackStack
                )
            }
        )
        codeListGraph(
            codeItemClickListener = { codeUuid ->
                navHostController.navigateToCodeDetails(
                    codeUuid = codeUuid
                )
            },
            nestedGraphs = {
                codeDetailsScreen(
                    onBackPressed = navHostController::popBackStack
                )
            }
        )
        settingsScreen()
    }
}