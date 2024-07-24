package com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.R
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.mvi.HistoryUiAction
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.mvi.HistoryUiState
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.viewmodel.HistoryViewModel
import com.t_ovchinnikova.android.scandroid_2.core_ui.CenterMessage
import com.t_ovchinnikova.android.scandroid_2.core_ui.CenterProgress
import com.t_ovchinnikova.android.scandroid_2.core_ui.SimpleAlertDialog
import org.koin.androidx.compose.koinViewModel
import java.util.UUID
import com.t_ovchinnikova.android.scandroid_2.core_resources.R as CoreResources

private const val MOTION_HEIGHT_KEY = "Motion height"
private const val PROGRESS_KEY = "Progress"

@Composable
fun HistoryScreen(
    codeItemClickListener: (codeId: UUID) -> Unit,
    viewModel: HistoryViewModel = koinViewModel<HistoryViewModel>()
) {
    val screenState by viewModel.uiState.collectAsState()

    HistoryContent(
        state = screenState,
        onAction = viewModel::onAction,
        codeItemClickListener = codeItemClickListener
    )
}

@Composable
fun HistoryContent(
    state: HistoryUiState,
    onAction: (HistoryUiAction) -> Unit,
    codeItemClickListener: (codeId: UUID) -> Unit
) {
    val deleteDialogState = rememberSaveable {
        mutableStateOf(false)
    }

    val lazyScrollState = rememberLazyListState()

    val collapsedState by remember {
        derivedStateOf {
            lazyScrollState.firstVisibleItemIndex !in 0..1
        }
    }

    val progress by animateFloatAsState(
        targetValue = if (collapsedState) 1f else 0f,
        tween(500), label = PROGRESS_KEY
    )
    val motionHeight by animateDpAsState(
        targetValue = if (collapsedState) 56.dp else 112.dp,
        tween(500), label = MOTION_HEIGHT_KEY
    )

    Scaffold(
        topBar = {
            HistoryAppBar(
                progress = progress,
                motionHeight = motionHeight,
                title = stringResource(id = CoreResources.string.history),
                onSearchEditingListener = { onAction(HistoryUiAction.UpdateSearchCondition(it)) },
                deleteClickListener = { deleteDialogState.value = true }
            )
        }
    ) { paddingValues ->
        when {
            state.isLoading -> {
                CenterProgress(message = stringResource(id = CoreResources.string.loading))
            }
            state.codes.isEmpty() -> {
                EmptyHistory()
            }
            else -> {
                HistoryList(
                    lazyScrollState = lazyScrollState,
                    paddingValues = paddingValues,
                    codes = state.codes,
                    onAction = onAction,
                    codeItemClickListener = codeItemClickListener
                )
            }
        }
    }

    if (deleteDialogState.value) {
        SimpleAlertDialog(
            title = stringResource(id = CoreResources.string.delete_question_dialog_title),
            subtitle = stringResource(id = R.string.delete_all_question_dialog),
            dismissClickListener = { deleteDialogState.value = false },
            dismissButtonText = stringResource(id = CoreResources.string.delete_dialog_cancel_button),
            confirmClickListener = {
                onAction.invoke(HistoryUiAction.DeleteAllCodes)
                deleteDialogState.value = false
            },
            confirmButtonText = stringResource(id = CoreResources.string.delete_dialog_delete_button)
        )
    }
}

@Composable
fun EmptyHistory() {
    CenterMessage(
        stringResource(id = R.string.the_list_is_empty_message),
        imageRes = CoreResources.drawable.ic_dissatisfied
    )
}
