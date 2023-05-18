package com.t_ovchinnikova.android.scandroid_2.code_details_impl.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.ui.CodeDetailsScreen
import java.util.UUID

const val KEY_CODE_ID = "code_id"
const val ROUTE_CODE_DETAILS = "code_details/{$KEY_CODE_ID}"
private const val ROUTE_FOR_ARGS = "code_details"

fun NavController.navigateToCodeDetails(
    navOptions: NavOptions? = null,
    codeUuid: UUID
) {
    this.navigate(getRouteWithArgs(codeUuid), navOptions)
}

fun NavGraphBuilder.codeDetailsScreen(
    onBackPressed: () -> Unit
) {
    this.composable(
        route = ROUTE_CODE_DETAILS,
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