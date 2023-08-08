package com.t_ovchinnikova.android.scandroid_2.core_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SimpleAlertDialog(
    title: String,
    subtitle: String,
    dismissClickListener: () -> Unit,
    dismissButtonText: String,
    confirmClickListener: (() -> Unit)? = null,
    confirmButtonText: String = EMPTY
) {
    AlertDialog(
        title = { Text(text = title, style = typography.h6) },
        text = {
            Text(subtitle)
        },
        confirmButton = {
            OutlinedButton(
                onClick = dismissClickListener,
                modifier = Modifier
                    .padding(8.dp)
                    .width(100.dp)
            ) {
                Text(text = dismissButtonText)
            }
            confirmClickListener?.let {
                Button(
                    onClick = it,
                    modifier = Modifier
                        .padding(8.dp)
                        .width(100.dp)
                ) {
                    Text(text = confirmButtonText)
                }
            }
        },
        onDismissRequest = dismissClickListener
    )
}

@Composable
fun AlertDialogWithTextField(
    title: String,
    subtitle: String? = null,
    text: String = EMPTY,
    textHint: String = EMPTY,
    dismissClickListener: () -> Unit,
    dismissButtonText: String,
    confirmClickListener: ((note: String) -> Unit),
    confirmButtonText: String = EMPTY
) {
    val textState = rememberSaveable { mutableStateOf(text) }

    AlertDialog(
        modifier = Modifier.wrapContentHeight(),
        title = { Text(text = title, style = typography.h6) },
        text = {
            subtitle?.let {
                Text(it)
            }
            TextField(
                textStyle = TextStyle(fontSize = 16.sp),
                value = textState.value,
                onValueChange = { textState.value = it },
                label = {
                    Text(text = textHint)
                },
                modifier = Modifier
                    .background(Color.Transparent),
                trailingIcon = {
                    if (textState.value != EMPTY) IconButton(
                        onClick = {
                            textState.value = EMPTY
                        },
                        modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Clear"
                        )
                    }
                }
            )
        },
        confirmButton = {
            OutlinedButton(
                onClick = dismissClickListener,
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = dismissButtonText)
            }
            Button(
                onClick = {
                    confirmClickListener(textState.value)
                    dismissClickListener()
                },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = confirmButtonText)
            }
        },
        onDismissRequest = dismissClickListener
    )
}
