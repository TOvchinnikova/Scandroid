package com.t_ovchinnikova.android.scandroid_2.code_details_impl.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.R
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.viewmodel.CodeDetailsViewModel
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_ui.ActionButton
import com.t_ovchinnikova.android.scandroid_2.core_ui.AlertDialogWithTextField
import com.t_ovchinnikova.android.scandroid_2.core_ui.CenterProgress
import com.t_ovchinnikova.android.scandroid_2.core_ui.DATE_PATTERN_STRING
import com.t_ovchinnikova.android.scandroid_2.core_ui.Divider
import com.t_ovchinnikova.android.scandroid_2.core_ui.SecondaryText
import com.t_ovchinnikova.android.scandroid_2.core_ui.SimpleAlertDialog
import com.t_ovchinnikova.android.scandroid_2.core_utils.copyToClipboard
import com.t_ovchinnikova.android.scandroid_2.core_utils.searchWeb
import com.t_ovchinnikova.android.scandroid_2.core_utils.shareText
import com.t_ovchinnikova.android.scandroid_2.core_utils.toStringByPattern
import com.t_ovchinnikova.android.scandroid_2.core_utils.toStringRes
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID

@Composable
fun CodeDetailsScreen(
    codeId: UUID,
    onBackPressed: () -> Unit,
    viewModel: CodeDetailsViewModel =  koinViewModel { parametersOf(codeId) }
) {
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
                CodeDetailsTopAppBar(
                    onBackPressed = onBackPressed,
                    title = stringResource(id = code.format.toStringRes()),
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
                    },
                    saveNoteClickListener = {
                        viewModel.updateBarcode(code.copy(note = it))
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
                onBackPressed()
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
    copyClickListener: () -> Unit,
    saveNoteClickListener: (note: String) -> Unit
) {

    val changeNoteDialogState = rememberSaveable {
        mutableStateOf(false)
    }

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
            IconButton(onClick = { changeNoteDialogState.value = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = null
                )
            }
        }
        if (code.note.isNotBlank()) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = code.note)
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = code.text)

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = stringResource(id = code.format.toStringRes()))

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

    if (changeNoteDialogState.value) {
        AlertDialogWithTextField(
            title = stringResource(id = R.string.note),
            text = code.note,
            dismissClickListener = { changeNoteDialogState.value = false },
            dismissButtonText = stringResource(id = R.string.delete_dialog_cancel_button),
            confirmClickListener = saveNoteClickListener,
            confirmButtonText = stringResource(id = R.string.save)
        )
    }
}

