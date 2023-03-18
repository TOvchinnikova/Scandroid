package com.t_ovchinnikova.android.scandroid_2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import java.util.*

fun NavGraphBuilder.historyScreenNavGraph(
    historyScreenContent: @Composable () -> Unit,
    codeInfoScreenContent: @Composable (codeId: UUID) -> Unit
) {
    navigation(
        startDestination = Screen.History.route,
        route = Screen.HistoryMain.route
    ) {
        composable(Screen.History.route) {
            historyScreenContent()
        }
        composable(
            route = Screen.CodeInfoFromHistory.route,
            arguments = listOf(
                navArgument(Screen.KEY_CODE_ID) {
                    type = Code.NavigationType
                }
            )
        ) {
            val codeId = it.arguments?.getSerializable(Screen.KEY_CODE_ID) as UUID?
                ?: throw RuntimeException("Args is null")
            codeInfoScreenContent(codeId)
        }
    }
}