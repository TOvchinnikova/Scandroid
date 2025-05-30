package com.t_ovchinnikova.android.scandroid_2.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.t_ovchinnikova.android.scandroid_2.core_ui.theme.getNavigationBottomBackgroundColor
import com.t_ovchinnikova.android.scandroid_2.navigation.AppNavGraph
import com.t_ovchinnikova.android.scandroid_2.navigation.NavigationState
import com.t_ovchinnikova.android.scandroid_2.navigation.rememberNavigationState

@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState()

    Scaffold(
        bottomBar = {
            BottomBar(navigationState)
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { paddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
        ) {
            AppNavGraph(navHostController = navigationState.navHostController)
        }
    }
}

@Composable
fun BottomBar(
    navigationState: NavigationState
) {
    NavigationBar(
        containerColor = getNavigationBottomBackgroundColor(),
        modifier = Modifier
            .navigationBarsPadding()
            .height(70.dp)
    ) {
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
            NavigationBarItem(
                modifier = Modifier
                    .background(getNavigationBottomBackgroundColor()),
                selected = selected,
                onClick = {
                    if (!selected) {
                        navigationState.navigateTo(item)
                    }
                },
                icon = {
                    Icon(painter = painterResource(id = item.iconResId), contentDescription = null)
                },
                label = {
                    Text(text = stringResource(id = item.titleResId))
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSecondary,
                    disabledIconColor = Color.Unspecified,
                    disabledTextColor = Color.Unspecified,
                    selectedIndicatorColor = Color.Unspecified
                ),
            )
        }
    }
}