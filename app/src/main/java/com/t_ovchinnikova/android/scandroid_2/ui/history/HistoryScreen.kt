package com.t_ovchinnikova.android.scandroid_2.ui.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.data.toStringRes
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.HistoryViewModel
import com.t_ovchinnikova.android.scandroid_2.ui.CenterMessage
import com.t_ovchinnikova.android.scandroid_2.ui.CenterProgress
import com.t_ovchinnikova.android.scandroid_2.ui.SecondaryText
import com.t_ovchinnikova.android.scandroid_2.ui.SimpleAlertDialog
import com.t_ovchinnikova.android.scandroid_2.ui.theme.ColorPrimary
import org.koin.androidx.compose.koinViewModel
import java.util.*

// todo здесь необходимо реализовать поиск по списку
// todo здесь необходимо реализовать удаление по свайпу влево
@Composable
fun HistoryScreen(
    codeItemClickListener: (codeId: UUID) -> Unit,
    viewModel: HistoryViewModel = koinViewModel<HistoryViewModel>()
) {

    val screenState = viewModel.codesHistoryStateFlow.collectAsState()

    val deleteDialogState = rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title =
                {
                    Text(text = stringResource(id = R.string.history))
                },
                actions = {
                    IconButton(onClick = { deleteDialogState.value = true }) {
                        Image(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        when(val state = screenState.value) {
            is HistoryScreenState.Loading -> {
                CenterProgress()
            }
            is HistoryScreenState.History -> {
                HistoryList(
                    paddingValues = paddingValues,
                    codes = state.codes,
                    onFavouriteClickListener = { viewModel.toggleFavourite(it) },
                    onRemoveListener = { viewModel.deleteCode(it) },
                    codeItemClickListener = codeItemClickListener,
                    isSaveBarcodesToHistory = state.isSaveBarcodesToHistory)
            }
            is HistoryScreenState.EmptyHistory -> {
                EmptyHistory(state.isSaveBarcodesToHistory)
            }
            is HistoryScreenState.Initial -> {

            }
        }
    }

    if (deleteDialogState.value) {
        SimpleAlertDialog(
            title = stringResource(id = R.string.delete_question_dialog_title),
            subtitle = stringResource(id = R.string.delete_all_question_dialog),
            dismissClickListener = { deleteDialogState.value = false },
            dismissButtonText = stringResource(id = R.string.delete_dialog_cancel_button),
            confirmClickListener = {
                viewModel.deleteAllCodes()
                deleteDialogState.value = false
            },
            confirmButtonText = stringResource(id = R.string.delete_dialog_delete_button)
        )
    }
}

@Composable
fun EmptyHistory(
    isSaveBarcodesToHistory: Boolean
) {
    CenterMessage(
        if (isSaveBarcodesToHistory) {
            stringResource(id = R.string.save_settings_disabled_message)
        } else {
            stringResource(id = R.string.the_list_is_empty_message)
        }
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HistoryList(
    paddingValues: PaddingValues,
    codes: List<Code>,
    onFavouriteClickListener: (code: Code) -> Unit,
    onRemoveListener: (codeId: UUID) -> Unit,
    codeItemClickListener: (codeId: UUID) -> Unit,
    isSaveBarcodesToHistory: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues),
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
            val dismissState = rememberDismissState()
            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                onRemoveListener(code.id)
            }
            SwipeToDismiss(
                modifier = Modifier.animateItemPlacement(),
                state = dismissState,
                background = {},
                directions = setOf(DismissDirection.EndToStart)
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

@Composable
fun HistoryItem(
    code: Code,
    onFavouriteClickListener: (code: Code) -> Unit,
    codeItemClickListener: (codeId: UUID) -> Unit,
    isSaveBarcodesToHistory: Boolean
) {
    val drawableResource =
        if (code.isFavorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off

    Card(
        modifier = Modifier
            .clickable { codeItemClickListener(code.id) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .background(
                        color = ColorPrimary,
                        shape = CircleShape
                    )
                    .padding(7.dp)
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_barcode),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = code.text
                )
                if (code.note.isNotBlank()) {
                    SecondaryText(
                        modifier = Modifier.padding(bottom = 4.dp),
                        text = code.note
                    )
                }
                SecondaryText(
                    text = code.date.toString()
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    enabled = isSaveBarcodesToHistory,
                    onClick = { onFavouriteClickListener(code) }
                ) {
                    Image(
                        painter = painterResource(id = drawableResource),
                        contentDescription = null
                    )
                }
                SecondaryText(
                    text = stringResource(id = code.format.toStringRes())
                )
            }
        }
    }
}
