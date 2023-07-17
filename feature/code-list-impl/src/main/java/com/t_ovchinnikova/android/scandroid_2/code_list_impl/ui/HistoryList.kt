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
import androidx.compose.ui.unit.dp
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HistoryList(
    lazyScrollState: LazyListState,
    paddingValues: PaddingValues,
    codes: List<Code>,
    onFavouriteClickListener: (code: Code) -> Unit,
    onRemoveListener: (codeId: UUID) -> Unit,
    codeItemClickListener: (codeId: UUID) -> Unit,
    isSaveBarcodesToHistory: Boolean
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
                },
                directions = setOf(DismissDirection.EndToStart),
                dismissThresholds = {
                    FractionalThreshold(0.66f)
                }
            ) {
                HistoryItem(
                    code = code,
                    onFavouriteClickListener = onFavouriteClickListener,
                    codeItemClickListener = codeItemClickListener,
                    isSaveBarcodesToHistory = isSaveBarcodesToHistory
                )
            }
        }
    }
}
