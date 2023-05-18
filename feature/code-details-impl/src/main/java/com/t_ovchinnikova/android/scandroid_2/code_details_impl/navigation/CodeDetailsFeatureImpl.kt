package com.t_ovchinnikova.android.scandroid_2.code_details_impl.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.t_ovchinnikova.android.scandroid_2.code_details_api.CodeDetailsFeature
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.ui.CodeDetailsScreen
import java.util.UUID

class CodeDetailsFeatureImpl : CodeDetailsFeature {

    override val route: String
        get() = ROUTE_CODE_DETAILS

    override fun navigateToCodeDetails(
        navController: NavController,
        codeUuid: UUID
    ) {
        navController.navigate(getRouteWithArgs(codeUuid))
    }

    override fun codeDetailsScreen(
        navGraphBuilder: NavGraphBuilder,
        onBackPressed: () -> Unit
    ) {
        navGraphBuilder.composable(
            route = route,
            arguments = listOf(
                navArgument(KEY_CODE_ID) {
                    type = CodeNavigationType
                }
            )
        ) {
            val codeId = it.arguments?.getSerializable(KEY_CODE_ID) as UUID?
                ?: throw RuntimeException("Args is null")
            CodeDetailsScreen(
                codeId = codeId,
                onBackPressed = onBackPressed
            )
        }
    }

    private fun getRouteWithArgs(codeId: UUID): String {
        return "$ROUTE_FOR_ARGS/$codeId"
    }

    companion object {
        const val KEY_CODE_ID = "code_id"
        const val ROUTE_CODE_DETAILS = "code_details/{$KEY_CODE_ID}"
        private const val ROUTE_FOR_ARGS = "code_details"
    }
}