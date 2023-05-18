package com.t_ovchinnikova.android.scandroid_2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import java.util.UUID

fun NavGraphBuilder.scannerScreenNavGraph(
    scannerScreenContent: @Composable () -> Unit,
    codeDetailsScreenContent: @Composable (codeId: UUID) -> Unit
) {
    navigation(
        startDestination = Screen.Scanner.route,
        route = Screen.ScannerMain.route
    ) {
        composable(Screen.Scanner.route) {
            scannerScreenContent()
        }
//        codeDetailsEntry.codeDetailsScreen()
//        composable(
//            route = Screen.CodeDetails.route,
//            arguments = listOf(
//                navArgument(KEY_CODE_ID) {
//                    type = CodeNavigationType
//                }
//            )
//        ) {
//            val codeId = it.arguments?.getSerializable(KEY_CODE_ID) as UUID?
//                ?: throw RuntimeException("Args is null")
//            codeDetailsScreenContent(codeId)
//        }

    }
}