package com.t_ovchinnikova.android.scandroid_2.ui.code_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.formatToStringId
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.CodeDetailsViewModel
import com.t_ovchinnikova.android.scandroid_2.ui.*
import com.t_ovchinnikova.android.scandroid_2.utils.*
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CodeDetailsScreen(
    codeId: UUID,
    onBackPressed: () -> Unit
) {
    val viewModel = koinViewModel<CodeDetailsViewModel>() {
        parametersOf(codeId)
    }

    val screenState = viewModel.screenStateFlow.collectAsState()

    val deleteDialogState = rememberSaveable {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    Column {
        when (val state = screenState.value) {
            is CodeDetailsScreenState.Loading, is CodeDetailsScreenState.Initial -> {
                CodeDetailsTopAppBar(onBackPressed = onBackPressed)
                CenterProgress()
            }
            is CodeDetailsScreenState.CodeDetails -> {
                val code = state.code
                if (state.isFromDatabase) {
                    CodeDetailsTopAppBar(
                        onBackPressed = onBackPressed,
                        title = stringResource(id = code.formatToStringId()),
                        onFavouriteClickListener = {
                            viewModel.updateBarcode(
                                code = code.copy(
                                    isFavorite = !code.isFavorite
                                )
                            )
                        },
                        onDeleteClickListener = {
                            deleteDialogState.value = true
                        },
                        isFavourite = code.isFavorite
                    )
                } else {
                    CodeDetailsTopAppBar(
                        onBackPressed = onBackPressed,
                        title = stringResource(id = code.formatToStringId())
                    )
                }
                Content(
                    code = code,
                    shareTextClickListener = {
                        context.shareText(code.text, code.note, state.isSendingNoteWithCode)
                    },
                    copyClickListener = {
                        context.copyToClipboard(code.text)
                    },
                    searchWebClickListener = {
                        context.searchWeb(code.text)
                    }
                )
            }
            is CodeDetailsScreenState.CodeNotFound -> {
                CodeDetailsTopAppBar(onBackPressed = onBackPressed)
            }
        }
    }

    if (deleteDialogState.value) {
        SimpleAlertDialog(
            title = stringResource(id = R.string.delete_question_dialog_title),
            subtitle = stringResource(id = R.string.delete_question_dialog),
            dismissClickListener = { deleteDialogState.value = false },
            dismissButtonText = stringResource(id = R.string.delete_dialog_cancel_button),
            confirmClickListener = {
                viewModel.deleteBarcode(codeId)
                deleteDialogState.value = false
                onBackPressed()
                deleteDialogState.value = false
            },
            confirmButtonText = stringResource(id = R.string.delete_dialog_delete_button)
        )
    }
}

@Composable
fun Content(
    code: Code,
    shareTextClickListener: () -> Unit,
    searchWebClickListener: () -> Unit,
    copyClickListener: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SecondaryText(
                text = code.date.toStringByPattern(
                    SimpleDateFormat(DATE_PATTERN_STRING, Locale.ENGLISH)
                )
            )
            Image(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = null
            )
        }
        if (code.note.isNotBlank()) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = code.note)
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = code.text)

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = stringResource(id = code.formatToStringId()))

        Divider()

        ActionButton(titleResId = R.string.copy_to_clipboard, iconResId = R.drawable.ic_copy) {
            copyClickListener()
        }

        ActionButton(titleResId = R.string.barcode_search, iconResId = R.drawable.ic_search) {
            searchWebClickListener()
        }

        ActionButton(titleResId = R.string.barcode_share_text, iconResId = R.drawable.ic_send) {
            shareTextClickListener()
        }
    }
}

