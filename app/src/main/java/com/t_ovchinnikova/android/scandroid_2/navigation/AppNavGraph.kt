package com.t_ovchinnikova.android.scandroid_2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import java.util.UUID

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    scannerScreenContent: @Composable () -> Unit,
    codeDetailsScreenContent: @Composable (codeId: UUID) -> Unit,
    historyScreenContent: @Composable () -> Unit,
    settingsScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.ScannerMain.route
    ) {
        scannerScreenNavGraph(
            scannerScreenContent,
            codeDetailsScreenContent
        )
        historyScreenNavGraph(
            historyScreenContent,
            codeDetailsScreenContent
        )
        composable(Screen.Settings.route) {
            settingsScreenContent()
        }
    }
}