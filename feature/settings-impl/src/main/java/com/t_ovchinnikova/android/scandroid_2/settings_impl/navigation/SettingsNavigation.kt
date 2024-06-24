package com.t_ovchinnikova.android.scandroid_2.settings_impl.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.t_ovchinnikova.android.scandroid_2.settings_impl.presentation.ui.SettingsScreen

const val ROUTE_SETTINGS = "settings"

fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    this.navigate(ROUTE_SETTINGS, navOptions)
}

fun NavGraphBuilder.settingsScreen() {
    composable(
        route = ROUTE_SETTINGS
    ) {
        SettingsScreen()
    }
}