package com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.R
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.CodeUiModel
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.mvi.HistoryUiAction
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.mvi.HistoryUiState
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.viewmodel.HistoryViewModel
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeFormat
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeType
import com.t_ovchinnikova.android.scandroid_2.core_ui.CenterMessage
import com.t_ovchinnikova.android.scandroid_2.core_ui.CenterProgress
import com.t_ovchinnikova.android.scandroid_2.core_ui.SimpleAlertDialog
import com.t_ovchinnikova.android.scandroid_2.core_ui.theme.ScandroidTheme
import org.koin.androidx.compose.koinViewModel
import java.util.Date
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
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            HistoryAppBar(
                progress = progress,
                motionHeight = motionHeight,
                title = stringResource(id = CoreResources.string.history),
                onAction = onAction,
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
                    isVisibleCheckBox = state.isVisibleCheckBox,
                    onAction = onAction,
                    codeItemClickListener = codeItemClickListener
                )
            }
        }
    }

    if (state.isVisibleDeleteDialog) {
        SimpleAlertDialog(
            title = stringResource(id = CoreResources.string.delete_question_dialog_title),
            subtitle = stringResource(id = R.string.delete_all_question_dialog),
            dismissClickListener = { onAction(HistoryUiAction.HideDeleteDialog) },
            dismissButtonText = stringResource(id = CoreResources.string.delete_dialog_cancel_button),
            confirmClickListener = {
                onAction(HistoryUiAction.DeleteAllCodes)
                onAction(HistoryUiAction.HideDeleteDialog)
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

@Preview
@Composable
fun HistoryContentPreviewLight() {
    HistoryContentPreview(false)
}

@Preview
@Composable
fun HistoryContentPreviewDark() {
    HistoryContentPreview(true)
}

@Composable
fun HistoryContentPreview(isDark: Boolean) {
    ScandroidTheme(isDark) {
        HistoryContent(
            state = HistoryUiState(
                isLoading = false,
                codes = listOf<CodeUiModel>(
                    CodeUiModel(
                        code = Code(
                            id = UUID.randomUUID(),
                            text = "12345678",
                            format = CodeFormat.DATA_MATRIX,
                            note = "Очень важный штрих-код",
                            date = Date(),
                            isFavorite = true,
                            type = CodeType.TEXT
                        )
                    ),
                    CodeUiModel(
                        code = Code(
                            id = UUID.randomUUID(),
                            text = "1234567891234",
                            format = CodeFormat.EAN_13,
                            date = Date(),
                            isFavorite = false,
                            type = CodeType.TEXT
                        )
                    ),
                    CodeUiModel(
                        code = Code(
                            id = UUID.randomUUID(),
                            text = "89585691785",
                            format = CodeFormat.QR_CODE,
                            date = Date(),
                            isFavorite = false,
                            type = CodeType.PHONE
                        )
                    ),
                )
            ),
            onAction = {},
            codeItemClickListener = {}
        )
    }
}
