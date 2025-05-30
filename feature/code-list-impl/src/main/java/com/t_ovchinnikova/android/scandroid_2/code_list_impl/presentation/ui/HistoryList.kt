package com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.CodeItemUiModel
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.CodeUiModel
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.mvi.HistoryUiAction
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeFormat
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeType
import com.t_ovchinnikova.android.scandroid_2.core_ui.theme.ScandroidTheme
import java.util.UUID

@Composable
fun HistoryList(
    paddingValues: PaddingValues,
    codes: List<CodeItemUiModel>,
    isVisibleCheckBox: Boolean,
    onAction: (HistoryUiAction) -> Unit,
    codeItemClickListener: (codeId: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        items(
            items = codes,
            key = { it.code.id }
        ) { codeUiModel ->
            val currentItem by rememberUpdatedState(codeUiModel)
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    when (it) {
                        SwipeToDismissBoxValue.EndToStart -> {
                            onAction.invoke(HistoryUiAction.DeleteCode(currentItem.code.id))
                            true
                        }
                        else -> { false }
                    }
                }
            )
            SwipeToDismissBox (
                modifier = Modifier.animateItem(),
                state = dismissState,
                backgroundContent = {
                    HistoryItemSwipeBackground()
                },
                enableDismissFromStartToEnd = false,
            ) {
                HistoryItem(
                    codeModel = codeUiModel,
                    isVisibleCheckBox = isVisibleCheckBox,
                    onAction = onAction,
                    codeItemClickListener = codeItemClickListener
                )
            }
        }
    }
}

@Composable
private fun HistoryItemSwipeBackground() {
    val color by animateColorAsState(Color.Red)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color, shape = MaterialTheme.shapes.medium),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier.padding(end = 14.dp),
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Preview
@Composable
private fun HistoryListPreviewLight() {
    HistoryListPreview(false)
}

@Preview
@Composable
private fun HistoryListPreviewDark() {
    HistoryListPreview(true)
}

@Composable
private fun HistoryListPreview(isDark: Boolean) {
    val codeList = listOf<CodeItemUiModel>(
        CodeItemUiModel(
            code = CodeUiModel(
                id = UUID.randomUUID().toString(),
                text = "12345678",
                format = CodeFormat.DATA_MATRIX,
                note = "Очень важный штрих-код",
                dateTime = "23.12.2024 13:31",
                isFavorite = true,
                type = CodeType.TEXT
            )
        ),
        CodeItemUiModel(
            code = CodeUiModel(
                id = UUID.randomUUID().toString(),
                text = "1234567891234",
                format = CodeFormat.EAN_13,
                dateTime = "23.12.2024 13:31",
                isFavorite = false,
                type = CodeType.TEXT
            )
        ),
        CodeItemUiModel(
            code = CodeUiModel(
                id = UUID.randomUUID().toString(),
                text = "89585691785",
                format = CodeFormat.QR_CODE,
                dateTime = "23.12.2024 13:31",
                isFavorite = false,
                type = CodeType.PHONE
            )
        ),
    )

    ScandroidTheme(isDark) {
        HistoryList(
            codes = codeList,
            isVisibleCheckBox = true,
            codeItemClickListener = {},
            paddingValues = PaddingValues(),
            onAction = {},
        )
    }
}
