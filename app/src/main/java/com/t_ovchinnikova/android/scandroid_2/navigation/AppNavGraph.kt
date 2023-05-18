package com.t_ovchinnikova.android.scandroid_2.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.t_ovchinnikova.android.scandroid_2.code_details_api.CodeDetailsFeature
import com.t_ovchinnikova.android.scandroid_2.code_list_api.CodeListFeature
import com.t_ovchinnikova.android.scandroid_2.scanner_api.ScannerFeature
import com.t_ovchinnikova.android.scandroid_2.settings_api.SettingsFeature

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    settingsFeature: SettingsFeature,
    scannerFeature: ScannerFeature,
    codeListFeature: CodeListFeature,
    codeDetailsFeature: CodeDetailsFeature
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.ScannerMain.route
    ) {
        scannerFeature.scannerGraph(
            navGraphBuilder = this,
            paddingValues = paddingValues,
            onScanListener = { codeUuid ->
                codeDetailsFeature.navigateToCodeDetails(
                    navController = navHostController,
                    codeUuid = codeUuid
                )
            },
            nestedGraphs = {
                codeDetailsFeature.codeDetailsScreen(
                    navGraphBuilder = this,
                    onBackPressed = navHostController::popBackStack
                )
            }
        )
        codeListFeature.codeListGraph(
            navGraphBuilder = this,
            codeItemClickListener = { codeUuid ->
                codeDetailsFeature.navigateToCodeDetails(
                    navController = navHostController,
                    codeUuid = codeUuid
                )
            },
            nestedGraphs = {
                codeDetailsFeature.codeDetailsScreen(
                    navGraphBuilder = this,
                    onBackPressed = navHostController::popBackStack
                )
            }
        )
        settingsFeature.navigateToSettings(navHostController)
    }
}