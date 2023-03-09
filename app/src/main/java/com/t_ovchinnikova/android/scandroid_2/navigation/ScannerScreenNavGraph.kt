package com.t_ovchinnikova.android.scandroid_2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.t_ovchinnikova.android.scandroid_2.domain.Code.Companion.NavigationType
import com.t_ovchinnikova.android.scandroid_2.navigation.Screen.Companion.KEY_CODE_ID
import java.util.UUID

fun NavGraphBuilder.scannerScreenNavGraph(
    scannerScreenContent: @Composable () -> Unit,
    codeInfoScreenContent: @Composable (codeId: UUID) -> Unit
) {
    navigation(
        startDestination = Screen.Scanner.route,
        route = Screen.Main.route
    ) {
        composable(Screen.Scanner.route) {
            scannerScreenContent()
        }
        composable(
            route = Screen.CodeInfo.route,
            arguments = listOf(
                navArgument(KEY_CODE_ID) {
                    type = NavigationType
                }
            )
        ) {
            val codeId = it.arguments?.getSerializable(KEY_CODE_ID) as UUID?
                ?: throw RuntimeException("Args is null")
            codeInfoScreenContent(codeId)
        }
    }

}