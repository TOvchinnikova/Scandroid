package com.t_ovchinnikova.android.scandroid_2.ui

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.t_ovchinnikova.android.scandroid_2.navigation.AppNavGraph
import com.t_ovchinnikova.android.scandroid_2.navigation.NavigationState
import com.t_ovchinnikova.android.scandroid_2.navigation.rememberNavigationState
import com.t_ovchinnikova.android.scandroid_2.ui.code_info.CodeInfoScreen
import com.t_ovchinnikova.android.scandroid_2.ui.history.HistoryScreen
import com.t_ovchinnikova.android.scandroid_2.ui.scanner.CameraPreview
import com.t_ovchinnikova.android.scandroid_2.ui.settings.SettingsScreen

@Composable
fun MainScreen() {

    val navigationState = rememberNavigationState()

    Scaffold(
        bottomBar = {
            BottomBar(navigationState)
        }
    ) { paddingValues ->  
        AppNavGraph(
            navHostController = navigationState.navHostController,
            scannerScreenContent = {
                CameraPreview(
                    paddingValues = paddingValues,
                    onScanListener = {
                        navigationState.navigateToCodeInfo(it)
                    }
                )
            },
            codeInfoScreenContent = {
                CodeInfoScreen(
                    codeId = it,
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    }
                )
            },
            historyScreenContent = {
                HistoryScreen(
                    codeItemClickListener = {
                        navigationState.navigateToCodeInfo(it)
                    }
                )
            },
            settingsScreenContent = {
                SettingsScreen()
            }
        )
    }
}

@Composable
fun BottomBar(
    navigationState: NavigationState
) {
    BottomNavigation {
        val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

        val items = listOf(
            NavigationItem.Scanner,
            NavigationItem.History,
            NavigationItem.Settings
        )
        items.forEach { item ->
            val selected = navBackStackEntry?.destination?.hierarchy?.any {
                it.route == item.screen.route
            } ?: false
            BottomNavigationItem(
                selected = selected,
                onClick = { if (!selected) {
                    navigationState.navigateTo(item.screen.route)
                }
                },
                icon = {
                    Icon(painter = painterResource(id = item.iconResId), contentDescription = null)
                },
                label = {
                    Text(text = stringResource(id = item.titleResId))
                },
                selectedContentColor = MaterialTheme.colors.onPrimary,
                unselectedContentColor = MaterialTheme.colors.onSecondary
            )
        }
    }
}