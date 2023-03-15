package com.t_ovchinnikova.android.scandroid_2.ui.code_info

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
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
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.formatToStringId
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.ScanResultViewModel
import com.t_ovchinnikova.android.scandroid_2.ui.ActionButton
import com.t_ovchinnikova.android.scandroid_2.ui.CenterProgress
import com.t_ovchinnikova.android.scandroid_2.ui.Divider
import com.t_ovchinnikova.android.scandroid_2.ui.SecondaryText
import com.t_ovchinnikova.android.scandroid_2.ui.theme.ColorPrimary
import com.t_ovchinnikova.android.scandroid_2.ui.theme.ScandroidTheme
import com.t_ovchinnikova.android.scandroid_2.utils.toStringByPattern
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CodeInfoScreen(
    codeId: UUID,
    onBackPressed: () -> Unit
) {
    val viewModel = koinViewModel<ScanResultViewModel>() {
        parametersOf(codeId)
    }

    val screenState = viewModel.screenStateFlow.collectAsState()
//    val appBarState = rememberSaveable {
//        mutableStateOf(CodeInfoAppBarState(onBackPressed = onBackPressed))
//    }

//    ScandroidTheme {
//        Scaffold(
//            topBar = {
//                CodeInfoTopAppBar(appBarState.value)
//            },
//        ) { paddingValues ->
    Column() {
        when (val state = screenState.value) {
            is CodeInfoScreenState.Loading -> {
                CodeInfoTopAppBar(onBackPressed = onBackPressed)
                CenterProgress()
            }
            is CodeInfoScreenState.CodeInfo -> {
                CodeInfoTopAppBar(onBackPressed = onBackPressed,
                    title = stringResource(id = state.code.formatToStringId()),
                    onFavouriteClickListener = {
                        viewModel.updateBarcode(
                            code = state.code.copy(
                                isFavorite = !state.code.isFavorite
                            )
                        )
                    },
                    onDeleteClickListener = {
                        viewModel.deleteBarcode(state.code.id)
                    },
                    isFavourite = state.code.isFavorite
                )
                Content(code = state.code)
            }
            is CodeInfoScreenState.CodeNotFound -> {

            }
            is CodeInfoScreenState.Initial -> {

            }
        }
    }
    }
//    }
//}

@Composable
fun CodeInfoTopAppBar(
    title: String = "",
    onBackPressed: () -> Unit,
    isFavourite: Boolean = false,
    onFavouriteClickListener: (() -> Unit)? = null,
    onDeleteClickListener: (() -> Unit)? = null
) {

    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        actions = {
            onFavouriteClickListener?.let { clickListener ->
                IconButton(onClick = { clickListener() }) {
                    Image(
                        painter = painterResource(id = if (isFavourite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off),
                        contentDescription = null
                    )
                }
            }
            onDeleteClickListener?.let { clickListener ->
                IconButton(onClick = { clickListener() }) {
                    Image(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            }
        }
    )
}

@Composable
fun Content(
    code: Code
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
            SecondaryText(text = code.date.toStringByPattern(SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH)))
            Image(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = code.note)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = code.text)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = stringResource(id = code.formatToStringId()))
        Divider()
        ActionButton(titleResId = R.string.copy_to_clipboard, iconResId = R.drawable.ic_copy) {
            Log.d("MyLog", "ActionButton clicked")
        }
        ActionButton(titleResId = R.string.barcode_search, iconResId = R.drawable.ic_search) {
            Log.d("MyLog", "ActionButton clicked")
        }
        ActionButton(titleResId = R.string.barcode_share_text, iconResId = R.drawable.ic_send) {
            Log.d("MyLog", "ActionButton clicked")
        }
    }
}