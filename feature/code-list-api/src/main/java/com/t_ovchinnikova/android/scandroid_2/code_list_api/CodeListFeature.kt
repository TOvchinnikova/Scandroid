package com.t_ovchinnikova.android.scandroid_2.code_list_api

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import java.util.UUID

interface CodeListFeature {

    val route: String

    fun navigateToCodeListGraph(
        navController: NavController
    )

    fun codeListGraph(
        navGraphBuilder: NavGraphBuilder,
        codeItemClickListener: (codeId: UUID) -> Unit,
        nestedGraphs: NavGraphBuilder.() -> Unit
    )
}