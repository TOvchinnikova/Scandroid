package com.t_ovchinnikova.android.scandroid_2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.navigation.navigateToCodeListGraph
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.navigation.navigateToScannerGraph
import com.t_ovchinnikova.android.scandroid_2.settings_impl.navigation.navigateToSettings
import com.t_ovchinnikova.android.scandroid_2.ui.NavigationItem

class NavigationState(
    val navHostController: NavHostController
) {
    fun navigateTo(navigationItem: NavigationItem) {
        //navHostController.navigate(route) {
        val navOptions = navOptions {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        when (navigationItem) {
            NavigationItem.Scanner ->  navHostController.navigateToScannerGraph(navOptions)
            NavigationItem.History -> navHostController.navigateToCodeListGraph(navOptions)
            NavigationItem.Settings -> navHostController.navigateToSettings(navOptions)
        }
        //}
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}