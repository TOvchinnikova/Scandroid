package com.t_ovchinnikova.android.scandroid_2.code_details_impl.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.R
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.presentation.model.mvi.CodeDetailsUiAction
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.presentation.model.mvi.CodeDetailsUiState
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.presentation.viewmodel.CodeDetailsViewModel
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeEntity
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeFormat
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeType
import com.t_ovchinnikova.android.scandroid_2.core_ui.ActionButton
import com.t_ovchinnikova.android.scandroid_2.core_ui.AlertDialogWithTextField
import com.t_ovchinnikova.android.scandroid_2.core_ui.CenterMessage
import com.t_ovchinnikova.android.scandroid_2.core_ui.CenterProgress
import com.t_ovchinnikova.android.scandroid_2.core_ui.DATE_PATTERN_STRING
import com.t_ovchinnikova.android.scandroid_2.core_ui.Divider
import com.t_ovchinnikova.android.scandroid_2.core_ui.SecondaryText
import com.t_ovchinnikova.android.scandroid_2.core_ui.SimpleAlertDialog
import com.t_ovchinnikova.android.scandroid_2.core_ui.theme.ScandroidTheme
import com.t_ovchinnikova.android.scandroid_2.core_utils.toStringByPattern
import com.t_ovchinnikova.android.scandroid_2.core_utils.toStringRes
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import com.t_ovchinnikova.android.scandroid_2.core_resources.R as CoreResources

@Composable
fun CodeDetailsScreen(
    codeId: UUID,
    onBackPressed: () -> Unit,
    viewModel: CodeDetailsViewModel =  koinViewModel { parametersOf(codeId) }
) {
    val screenState by viewModel.uiState.collectAsState()

    CodeDetailsContent(
        state = screenState,
        onAction = remember {
            viewModel::onAction
        },
        onBackPressed = remember {
            onBackPressed
        }
    )
}

@Composable
fun CodeDetailsContent(
    state: CodeDetailsUiState,
    onAction: (CodeDetailsUiAction) -> Unit,
    onBackPressed: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            CodeDetailsTopAppBar(
                onBackPressed = onBackPressed,
                title = state.toolbarTitle,
                onAction = onAction,
                isFavourite = state.code?.isFavorite ?: false,
                isVisibleButtons = state.code != null && !state.isLoading
            )
        }
    ) { paddings ->
        when {
            state.isLoading -> {
                CenterProgress(message = stringResource(id = CoreResources.string.loading))
            }

            state.code == null -> {
                CenterMessage(
                    message = stringResource(id = R.string.code_not_found),
                    imageRes = CoreResources.drawable.ic_dissatisfied
                )
            }

            else -> {
                Content(
                    code = state.code,
                    isVisibleCommentDialog = state.isVisibleCommentDialog,
                    onAction = onAction,
                    paddings = paddings
                )
            }
        }
    }

    if (state.isVisibleDeleteDialog) {
        SimpleAlertDialog(
            title = stringResource(id = CoreResources.string.delete_question_dialog_title),
            subtitle = stringResource(id = CoreResources.string.delete_question_dialog),
            dismissClickListener = { onAction(CodeDetailsUiAction.HideDeleteDialog) },
            dismissButtonText = stringResource(id = CoreResources.string.delete_dialog_cancel_button),
            confirmClickListener = {
                onAction(CodeDetailsUiAction.DeleteBarcode)
                onBackPressed()
            },
            confirmButtonText = stringResource(id = CoreResources.string.delete_dialog_delete_button)
        )
    }
}

@Composable
fun Content(
    code: CodeEntity,
    isVisibleCommentDialog: Boolean,
    onAction: (CodeDetailsUiAction) -> Unit,
    paddings: PaddingValues
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddings)
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SecondaryText(
                text = code.date.toStringByPattern(
                    SimpleDateFormat(DATE_PATTERN_STRING, Locale.ENGLISH)
                )
            )
            IconButton(onClick = { onAction(CodeDetailsUiAction.ShowCommentDialog) }) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = null
                )
            }
        }
        if (code.note.isNotBlank()) {
            Text(text = code.note)
            Spacer(modifier = Modifier.height(10.dp))
        }

        Text(text = code.text)

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = stringResource(id = code.format.toStringRes()))

        Divider()

        ActionButton(titleResId = R.string.copy_to_clipboard, iconResId = R.drawable.ic_copy) {
            onAction(CodeDetailsUiAction.CopyCodeValueToClipboard)
        }

        ActionButton(titleResId = CoreResources.string.barcode_search, iconResId = R.drawable.ic_search) {
            onAction(CodeDetailsUiAction.SearchOnWeb(context))
        }

        ActionButton(titleResId = CoreResources.string.barcode_share_text, iconResId = R.drawable.ic_send) {
             onAction(CodeDetailsUiAction.ShareCodeValue(context))
        }
    }

    if (isVisibleCommentDialog) {
        AlertDialogWithTextField(
            title = stringResource(id = CoreResources.string.note),
            text = code.note,
            dismissClickListener = { onAction(CodeDetailsUiAction.HideCommentDialog) },
            dismissButtonText = stringResource(id = CoreResources.string.delete_dialog_cancel_button),
            confirmClickListener = { onAction(CodeDetailsUiAction.NoteChanged(it)) },
            confirmButtonText = stringResource(id = CoreResources.string.save)
        )
    }
}

@Preview
@Composable
fun CodeDetailsPreview() {
    ScandroidTheme(false) {
        CodeDetailsContent(
            state = CodeDetailsUiState(
                code = CodeEntity(
                    id = UUID.randomUUID(),
                    text = "12345678",
                    format = CodeFormat.DATA_MATRIX,
                    note = "Очень важный штрих-код",
                    date = Date(),
                    isFavorite = true,
                    type = CodeType.TEXT
                ),
                isLoading = false,
            ),
            onAction = {},
            onBackPressed = {}
        )
    }
}

@Preview
@Composable
fun CodeDetailsPreviewDark() {
    ScandroidTheme(true) {
        CodeDetailsContent(
            state = CodeDetailsUiState(
                code = CodeEntity(
                    id = UUID.randomUUID(),
                    text = "12345678",
                    format = CodeFormat.DATA_MATRIX,
                    note = "Очень важный штрих-код",
                    date = Date(),
                    isFavorite = true,
                    type = CodeType.TEXT
                ),
                isLoading = false,
            ),
            onAction = {},
            onBackPressed = {}
        )
    }
}

@Preview
@Composable
fun CodeDetailsCodeNotFoundPreviewDark() {
    ScandroidTheme(true) {
        CodeDetailsContent(
            state = CodeDetailsUiState(
                code = null,
                isLoading = false,
            ),
            onAction = {},
            onBackPressed = {}
        )
    }
}

@Preview
@Composable
fun CodeDetailsCodeNotFoundPreview() {
    ScandroidTheme() {
        CodeDetailsContent(
            state = CodeDetailsUiState(
                code = null,
                isLoading = false,
            ),
            onAction = {},
            onBackPressed = {}
        )
    }
}

@Preview
@Composable
fun CodeDetailsLoadingPreviewDark() {
    ScandroidTheme(true) {
        CodeDetailsContent(
            state = CodeDetailsUiState(
                code = null,
                isLoading = true,
            ),
            onAction = {},
            onBackPressed = {}
        )
    }
}

@Preview
@Composable
fun CodeDetailsLoadingPreview() {
    ScandroidTheme() {
        CodeDetailsContent(
            state = CodeDetailsUiState(
                code = null,
                isLoading = true,
            ),
            onAction = {},
            onBackPressed = {}
        )
    }
}

