package com.t_ovchinnikova.android.scandroid_2.code_list_impl

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.t_ovchinnikova.android.scandroid_2.code_list_api.CodeListFeature
import java.util.UUID

class CodeListFeatureImpl : CodeListFeature {

    override val route: String
        get() = ROUTE_HISTORY

    override fun navigateToCodeListGraph(navController: NavController) {
        navController.navigate(route)
    }

    override fun codeListGraph(
        navGraphBuilder: NavGraphBuilder,
        codeItemClickListener: (codeId: UUID) -> Unit,
        nestedGraphs: NavGraphBuilder.() -> Unit
    ) {
        navGraphBuilder.navigation(
            route = GRAPH_HISTORY,
            startDestination = route
        ) {
            composable(route = ROUTE_HISTORY) {
                HistoryScreen(
                    codeItemClickListener = codeItemClickListener
                )
            }
            nestedGraphs()
        }
    }

    companion object {
        const val ROUTE_HISTORY = "history"
        const val GRAPH_HISTORY = "history_graph"
    }
}