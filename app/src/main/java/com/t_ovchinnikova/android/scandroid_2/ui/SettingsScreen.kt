package com.t_ovchinnikova.android.scandroid_2.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun SettingsScreen() {
    SettingsItem(title = "Вибрация", hint = "Вибрация при сканировании", isChecked = true, clickListener = null)
}

@Composable
fun SettingsItem(
    title: String,
    hint: String,
    isChecked: Boolean,
    clickListener: ((Boolean) -> Unit)?
) {
    Row {
        Column {
            Text(text = title)
            SecondaryText(text = hint)
        }
        Checkbox(checked = isChecked, onCheckedChange = clickListener)
    }
}