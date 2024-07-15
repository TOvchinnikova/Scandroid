package com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.ui.HistoryScreen
import java.util.UUID

const val ROUTE_HISTORY = "history"
const val GRAPH_HISTORY = "history_graph"

fun NavController.navigateToCodeListGraph(navOptions: NavOptions? = null) {
    this.navigate(GRAPH_HISTORY, navOptions)
}

fun NavGraphBuilder.codeListGraph(
    codeItemClickListener: (codeId: UUID) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = GRAPH_HISTORY,
        startDestination = ROUTE_HISTORY
    ) {
        composable(route = ROUTE_HISTORY) {
            HistoryScreen(
                codeItemClickListener = codeItemClickListener
            )
        }
        nestedGraphs()
    }
}