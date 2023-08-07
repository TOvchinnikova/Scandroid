package com.t_ovchinnikova.android.scandroid_2.ui

import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.t_ovchinnikova.android.scandroid_2.navigation.AppNavGraph
import com.t_ovchinnikova.android.scandroid_2.navigation.NavigationState
import com.t_ovchinnikova.android.scandroid_2.navigation.rememberNavigationState

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
            paddingValues = paddingValues
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
                it.route == item.route
            } ?: false
            BottomNavigationItem(
                selected = selected,
                onClick = { if (!selected) {
                    navigationState.navigateTo(item)
                }
                },
                icon = {
                    Icon(painter = painterResource(id = item.iconResId), contentDescription = null)
                },
                label = {
                    Text(text = stringResource(id = item.titleResId))
                },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.onSecondary
            )
        }
    }
}