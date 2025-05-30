package com.t_ovchinnikova.android.scandroid_2.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.presentation.navigation.codeDetailsScreen
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.presentation.navigation.navigateToCodeDetails
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.navigation.codeListGraph
import com.t_ovchinnikova.android.scandroid_2.core_ui.MenuItemScreen
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.presentation.navigation.GRAPH_SCANNER
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.presentation.navigation.scannerGraph
import com.t_ovchinnikova.android.scandroid_2.settings_impl.navigation.settingsScreen
import java.util.UUID

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = GRAPH_SCANNER,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        scannerGraph(
            onScanListener = { codeUuid ->
                navHostController.navigateToCodeDetails(
                    codeUuid = codeUuid,
                    parentScreen = MenuItemScreen.SCANNER
                )
            },
            nestedGraphs = {
                codeDetailsScreen(
                    onBackPressed = navHostController::popBackStack,
                    parentScreen = MenuItemScreen.SCANNER
                )
            }
        )
        codeListGraph(
            codeItemClickListener = { codeUuid ->
                navHostController.navigateToCodeDetails(
                    codeUuid = UUID.fromString(codeUuid),
                    parentScreen = MenuItemScreen.HISTORY
                )
            },
            nestedGraphs = {
                codeDetailsScreen(
                    onBackPressed = navHostController::popBackStack,
                    parentScreen = MenuItemScreen.HISTORY
                )
            }
        )
        settingsScreen()
    }
}