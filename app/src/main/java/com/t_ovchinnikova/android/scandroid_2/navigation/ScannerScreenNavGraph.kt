package com.t_ovchinnikova.android.scandroid_2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.scannerScreenNavGraph(
    scannerScreenContent: @Composable () -> Unit,
    codeInfoScreenContent: @Composable () -> Unit
) {
    navigation(
        startDestination = Screen.Scanner.route,
        route = Screen.Main.route
    ) {
        composable(Screen.Scanner.route) {
            scannerScreenContent()
        }
        composable(Screen.CodeInfo.route) {
            codeInfoScreenContent()
        }
    }

}