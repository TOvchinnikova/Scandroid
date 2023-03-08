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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.HistoryViewModel
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.ScanResultViewModel
import com.t_ovchinnikova.android.scandroid_2.ui.DividerPrimaryColor
import com.t_ovchinnikova.android.scandroid_2.ui.SecondaryText
import com.t_ovchinnikova.android.scandroid_2.ui.theme.ColorPrimary
import com.t_ovchinnikova.android.scandroid_2.ui.theme.ScandroidTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun CodeInfoScreen(
    onBackPressed: () -> Unit,
) {
    val viewModel = koinViewModel<ScanResultViewModel>()

    val screenState = viewModel.screenStateFlow.collectAsState()

    ScandroidTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "EAN-13")
                    },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = null)
                        }
                    },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_favorite_on),
                                contentDescription = null
                            )
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Image(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        }
                    }
                )
            },
        ) {
            Content()
        }
    }
}

@Composable
fun Content() {
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
            SecondaryText(text = "13.12.2022 22:52")
            Image(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Заметка")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "889958375835934")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Текст")
        DividerPrimaryColor()
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

@Composable
fun ActionButton(
    titleResId: Int,
    iconResId: Int,
    onButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .clickable(enabled = true, onClick = onButtonClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(shape = CircleShape, color = ColorPrimary)
        ) {
            Icon(
                modifier = Modifier.padding(10.dp).size(16.dp),
                painter = painterResource(id = iconResId),
                contentDescription = null,
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = stringResource(id = titleResId))
    }
}