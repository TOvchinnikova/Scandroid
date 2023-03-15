package com.t_ovchinnikova.android.scandroid_2.ui.code_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.formatToStringId
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.CodeInfoViewModel
import com.t_ovchinnikova.android.scandroid_2.ui.ActionButton
import com.t_ovchinnikova.android.scandroid_2.ui.CenterProgress
import com.t_ovchinnikova.android.scandroid_2.ui.Divider
import com.t_ovchinnikova.android.scandroid_2.ui.SecondaryText
import com.t_ovchinnikova.android.scandroid_2.utils.*
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CodeInfoScreen(
    codeId: UUID,
    onBackPressed: () -> Unit
) {
    val viewModel = koinViewModel<CodeInfoViewModel>() {
        parametersOf(codeId)
    }

    val screenState = viewModel.screenStateFlow.collectAsState()

    val context = LocalContext.current

    Column {
        when (val state = screenState.value) {
            is CodeInfoScreenState.Loading -> {
                CodeInfoTopAppBar(onBackPressed = onBackPressed)
                CenterProgress()
            }
            is CodeInfoScreenState.CodeInfo -> {
                val code = state.code
                if (state.isFromDatabase) {
                    CodeInfoTopAppBar(
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
                            viewModel.deleteBarcode(code.id)
                        },
                        isFavourite = code.isFavorite
                    )
                } else {
                    CodeInfoTopAppBar(
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
            is CodeInfoScreenState.CodeNotFound -> {
                CodeInfoTopAppBar(onBackPressed = onBackPressed)
            }
            is CodeInfoScreenState.Initial -> {

            }
        }
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

