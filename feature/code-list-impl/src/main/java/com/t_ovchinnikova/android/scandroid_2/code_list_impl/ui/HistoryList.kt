package com.t_ovchinnikova.android.scandroid_2.code_list_impl.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeFormat
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeType
import com.t_ovchinnikova.android.scandroid_2.core_ui.theme.ScandroidTheme
import java.util.Date
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HistoryList(
    lazyScrollState: LazyListState,
    paddingValues: PaddingValues,
    codes: List<Code>,
    onFavouriteClickListener: (code: Code) -> Unit,
    onRemoveListener: (codeId: UUID) -> Unit,
    codeItemClickListener: (codeId: UUID) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .animateContentSize(),
        state = lazyScrollState,
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 72.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        items(
            items = codes,
            key = { it.id }
        ) { code ->
            val currentItem by rememberUpdatedState(code)
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    when (it) {
                        DismissValue.DismissedToStart -> {
                            onRemoveListener(currentItem.id)
                            true
                        }
                        else -> { false }
                    }
                }
            )
            SwipeToDismiss(
                modifier = Modifier.animateItemPlacement(),
                state = dismissState,
                background = {
                    HistoryItemSwipeBackground()
                },
                directions = setOf(DismissDirection.EndToStart),
                dismissThresholds = {
                    FractionalThreshold(0.66f)
                }
            ) {
                HistoryItem(
                    code = code,
                    onFavouriteClickListener = onFavouriteClickListener,
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
    val codeList = listOf<Code>(
        Code(
            id = UUID.randomUUID(),
            text = "12345678",
            format = CodeFormat.DATA_MATRIX,
            note = "Очень важный штрих-код",
            date = Date(),
            isFavorite = true,
            type = CodeType.TEXT
        ),
        Code(
            id = UUID.randomUUID(),
            text = "1234567891234",
            format = CodeFormat.EAN_13,
            date = Date(),
            isFavorite = false,
            type = CodeType.TEXT
        ),
        Code(
            id = UUID.randomUUID(),
            text = "89585691785",
            format = CodeFormat.QR_CODE,
            date = Date(),
            isFavorite = false,
            type = CodeType.PHONE
        ),
    )

    ScandroidTheme(isDark) {
        HistoryList(
            lazyScrollState = rememberLazyListState(),
            paddingValues = PaddingValues(0.dp),
            codes = codeList,
            codeItemClickListener = {},
            onFavouriteClickListener = {},
            onRemoveListener = {}
        )
    }
}
