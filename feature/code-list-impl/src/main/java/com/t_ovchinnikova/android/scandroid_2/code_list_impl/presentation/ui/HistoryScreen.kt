package com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.R
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.mvi.HistoryUiAction
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.mvi.HistoryUiState
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.viewmodel.HistoryViewModel
import com.t_ovchinnikova.android.scandroid_2.core_ui.CenterMessage
import com.t_ovchinnikova.android.scandroid_2.core_ui.CenterProgress
import com.t_ovchinnikova.android.scandroid_2.core_ui.EMPTY
import com.t_ovchinnikova.android.scandroid_2.core_ui.SimpleAlertDialog
import com.t_ovchinnikova.android.scandroid_2.core_ui.theme.SearchFieldBackgroundColor
import org.koin.androidx.compose.koinViewModel
import com.t_ovchinnikova.android.scandroid_2.core_resources.R as CoreResources

@Composable
fun HistoryScreen(
    codeItemClickListener: (codeId: String) -> Unit,
    viewModel: HistoryViewModel = koinViewModel<HistoryViewModel>()
) {
    val screenState by viewModel.uiState.collectAsState()

    HistoryContent(
        state = screenState,
        onAction = viewModel::onAction,
        codeItemClickListener = codeItemClickListener
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryContent(
    state: HistoryUiState,
    onAction: (HistoryUiAction) -> Unit,
    codeItemClickListener: (codeId: String) -> Unit
) {
    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = CoreResources.string.history),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.titleLarge,
                        )
                    },
                    actions = {
                        Image(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clickable { onAction(HistoryUiAction.ShowDeleteDialog) },
                            imageVector = Icons.Filled.Delete,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                        )
                    }
                )
                SearchField(searchCondition = state.searchCondition, onAction = onAction)
            }
        },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchField(
    searchCondition: String,
    onAction: (HistoryUiAction) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    BasicTextField(
        value = searchCondition,
        singleLine = true,
        onValueChange = {
            onAction(HistoryUiAction.UpdateSearchCondition(it))
        },
        interactionSource = interactionSource,
        textStyle = TextStyle(fontSize = 16.sp),
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
            .background(
                color = SearchFieldBackgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxWidth()

    ) { innerTextField ->
        TextFieldDefaults.DecorationBox(
            value = searchCondition,
            visualTransformation = VisualTransformation.None,
            innerTextField = innerTextField,
            singleLine = true,
            enabled = true,
            interactionSource = interactionSource,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = SearchFieldBackgroundColor,
                unfocusedContainerColor = SearchFieldBackgroundColor,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.secondary,
                focusedPlaceholderColor = MaterialTheme.colorScheme.secondary,
            ),
            placeholder = {
                Text(
                    text = stringResource(id = CoreResources.string.barcode_search_on_list),
                    modifier = Modifier.padding(0.dp)
                )
            },
            contentPadding = PaddingValues(0.dp),
            trailingIcon = {
                if (searchCondition != EMPTY) IconButton(
                    onClick = {
                        onAction(HistoryUiAction.UpdateSearchCondition(EMPTY))
                    },
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Clear",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
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
