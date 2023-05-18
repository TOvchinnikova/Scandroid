package com.t_ovchinnikova.android.scandroid_2.settings_api

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface SettingsFeature {

    val route: String

    fun navigateToSettings(
        navController: NavController
    )

    fun settingsScreen(
        navGraphBuilder: NavGraphBuilder
    )
}