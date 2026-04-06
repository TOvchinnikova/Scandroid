package com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxState.Companion.Saver
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.CodeItemUiModel
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.mvi.HistoryUiAction
import kotlinx.coroutines.launch

@Composable
fun LazyItemScope.SwipeToDismissView(
    currentItem: CodeItemUiModel,
    onAction: (HistoryUiAction) -> Unit,
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    var width by remember { mutableIntStateOf(0) }
    val dismissState = rememberSaveable(
        currentItem,
        saver = Saver(positionalThreshold = { width / 2f }),
    ) {
        SwipeToDismissBoxState(SwipeToDismissBoxValue.Settled, density) { width / 2f }
    }

    when (dismissState.currentValue) {
        SwipeToDismissBoxValue.StartToEnd -> false
        SwipeToDismissBoxValue.EndToStart -> true.also {
            if (dismissState.progress == 1f) {
                onAction(HistoryUiAction.DeleteCode(currentItem.code.id))
                scope.launch {
                    dismissState.snapTo(SwipeToDismissBoxValue.Settled)
                }
            }
        }

        SwipeToDismissBoxValue.Settled -> false
    }
    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        modifier = Modifier
            .animateItem()
            .onSizeChanged {
                width = it.width
            }
            .fillMaxWidth(),
        backgroundContent = {
            Row(
                modifier = Modifier.fillMaxSize().background(Color.Red, shape = MaterialTheme.shapes.medium),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.padding(end = 14.dp),
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        content = { content() },
    )
}