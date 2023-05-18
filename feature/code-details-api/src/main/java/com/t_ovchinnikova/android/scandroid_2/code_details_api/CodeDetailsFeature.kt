package com.t_ovchinnikova.android.scandroid_2.code_details_api

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import java.util.UUID

interface CodeDetailsFeature {

    val route: String

    fun navigateToCodeDetails(
        navController: NavController,
        codeUuid: UUID
    )

    fun codeDetailsScreen(
        navGraphBuilder: NavGraphBuilder,
        onBackPressed: () -> Unit
    )
}