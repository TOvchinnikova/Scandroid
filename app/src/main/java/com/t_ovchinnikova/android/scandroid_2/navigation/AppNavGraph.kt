package com.t_ovchinnikova.android.scandroid_2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    scannerScreenContent: @Composable () -> Unit,
    codeInfoScreenContent: @Composable () -> Unit,
    historyScreenContent: @Composable () -> Unit,
    settingsScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Main.route
    ) {
        scannerScreenNavGraph(
            scannerScreenContent,
            codeInfoScreenContent
        )
        composable(Screen.History.route) {
            historyScreenContent()
        }
        composable(Screen.Settings.route) {
            settingsScreenContent()
        }
    }
}