package com.t_ovchinnikova.android.scandroid_2.settings_impl

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.t_ovchinnikova.android.scandroid_2.settings_api.SettingsFeature

class SettingsFeatureImpl : SettingsFeature {
    override val route: String
        get() = ROUTE_SETTINGS

    override fun navigateToSettings(navController: NavController) {
        navController.navigate(route)
    }

    override fun settingsScreen(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable(
            route = route
        ) {
            SettingsScreen()
        }
    }

    companion object {
        const val ROUTE_SETTINGS = "settings"
    }
}