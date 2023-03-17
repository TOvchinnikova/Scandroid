package com.t_ovchinnikova.android.scandroid_2.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.t_ovchinnikova.android.scandroid_2.utils.EMPTY_STRING

@Composable
fun CustomAlertDialog(
    title: String,
    subtitle: String,
    dismissClickListener: () -> Unit,
    dismissButtonText: String,
    confirmClickListener: (() -> Unit)? = null,
    confirmButtonText: String = EMPTY_STRING
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
