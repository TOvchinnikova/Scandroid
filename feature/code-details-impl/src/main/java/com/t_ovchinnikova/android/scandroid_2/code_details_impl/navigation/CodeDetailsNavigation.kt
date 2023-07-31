package com.t_ovchinnikova.android.scandroid_2.code_details_impl.navigation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.IntSize
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.ui.CodeDetailsScreen
import com.t_ovchinnikova.android.scandroid_2.core_ui.MenuItemScreen
import com.t_ovchinnikova.android.scandroid_2.core_utils.getSerializableArgument
import java.util.UUID

const val KEY_CODE_ID = "code_id"
const val ROUTE_CODE_DETAILS = "code_details/{$KEY_CODE_ID}"
private const val ROUTE_FOR_ARGS = "code_details"
const val ROUTE_CODE_DETAILS_HISTORY = "code_details_history/{$KEY_CODE_ID}"
private const val ROUTE_FOR_ARGS_HISTORY = "code_details_history"

fun NavController.navigateToCodeDetails(
    navOptions: NavOptions? = null,
    codeUuid: UUID,
    parentScreen: MenuItemScreen
) {
    this.navigate(getRouteWithArgs(codeUuid, parentScreen), navOptions)
}

fun NavGraphBuilder.codeDetailsScreen(
    onBackPressed: () -> Unit,
    parentScreen: MenuItemScreen
) {
    this.composable(
        route = getRoute(parentScreen),
        arguments = listOf(
            navArgument(KEY_CODE_ID) {
                type = CodeNavigationType
            }
        )
    ) {
        val codeId = it.arguments?.getSerializableArgument(KEY_CODE_ID, UUID::class.java)
            ?: throw RuntimeException("Args is null")

        CodeDetailsScreen(
            codeId = codeId,
            onBackPressed = onBackPressed
        )
    }
}

private fun getRouteWithArgs(
    codeId: UUID,
    parentScreen: MenuItemScreen
): String {
    return if (parentScreen == MenuItemScreen.SCANNER) {
        "$ROUTE_FOR_ARGS/$codeId"
    } else {
        "$ROUTE_FOR_ARGS_HISTORY/$codeId"
    }
}

private fun getRoute(
    parentScreen: MenuItemScreen
): String {
    return if (parentScreen == MenuItemScreen.SCANNER) {
        ROUTE_CODE_DETAILS
    } else {
        ROUTE_CODE_DETAILS_HISTORY
    }
}