package com.t_ovchinnikova.android.scandroid_2.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.ui.theme.ColorPrimary
import com.t_ovchinnikova.android.scandroid_2.ui.theme.ScandroidTheme

@Preview
@Composable
fun SettingsScreen() {
    ScandroidTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.settings))
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            ) {
                SettingsItem(
                    titleResId = R.string.fragment_settings_vibrate,
                    hintResId = R.string.fragment_settings_vibrate_hint,
                    isChecked = true,
                    clickListener = null
                )
                Divider(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .background(ColorPrimary)
                        .height(1.dp)
                )
                SettingsItem(
                    titleResId = R.string.fragment_settings_flash,
                    hintResId = R.string.fragment_settings_flash_hint,
                    isChecked = true,
                    clickListener = null
                )
                Divider(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .background(ColorPrimary)
                        .height(1.dp)
                )
                SettingsItem(
                    titleResId = R.string.fragment_settings_save_scanned_barcodes_to_history,
                    hintResId = R.string.fragment_settings_save_scanned_barcodes_to_history_hint,
                    isChecked = false,
                    clickListener = null
                )
                Divider(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .background(ColorPrimary)
                        .height(1.dp)
                )
                SettingsItem(
                    titleResId = R.string.fragment_settings_sending_note,
                    hintResId = R.string.fragment_settings_sending_note_hint,
                    isChecked = true,
                    clickListener = null
                )
                Divider(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .background(ColorPrimary)
                        .height(1.dp)
                )
            }
        }
    }
}

@Composable
fun SettingsItem(
    titleResId: Int,
    hintResId: Int,
    isChecked: Boolean,
    clickListener: ((Boolean) -> Unit)?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = stringResource(id = titleResId))
            Spacer(modifier = Modifier.height(4.dp))
            SecondaryText(text = stringResource(id = hintResId))
        }
        Switch(checked = isChecked, onCheckedChange = clickListener)
    }
}