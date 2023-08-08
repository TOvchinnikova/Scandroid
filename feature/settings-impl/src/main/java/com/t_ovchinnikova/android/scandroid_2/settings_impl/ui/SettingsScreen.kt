package com.t_ovchinnikova.android.scandroid_2.settings_impl.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.t_ovchinnikova.android.scandroid_2.core_ui.CenterProgress
import com.t_ovchinnikova.android.scandroid_2.core_ui.Divider
import com.t_ovchinnikova.android.scandroid_2.core_ui.SecondaryText
import com.t_ovchinnikova.android.scandroid_2.settings_api.entity.SettingsData
import com.t_ovchinnikova.android.scandroid_2.settings_impl.R
import com.t_ovchinnikova.android.scandroid_2.settings_impl.viewmodel.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel<SettingsViewModel>()
) {
    val screenState = viewModel.screenState.collectAsState(initial = SettingsScreenState.Initial)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.settings))
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp
            )
        }
    ) { paddingValues ->
        when (val currentScreenState = screenState.value) {
            is SettingsScreenState.SettingsScreen -> {
                ShowSettings(
                    modifier = Modifier.padding(paddingValues),
                    settingsData = currentScreenState.settings
                ) { settingsData ->
                    viewModel.saveSettings(
                        settingsData
                    )
                }
            }
            is SettingsScreenState.LoadingSettings -> {
                CenterProgress()
            }
            is SettingsScreenState.Initial -> {
                // nothing
            }
            is SettingsScreenState.Failure -> {
                // todo добавить обработку ошибок
            }
        }
    }
}

@Composable
private fun ShowSettings(
    modifier: Modifier = Modifier,
    settingsData: SettingsData,
    onClickListener: (SettingsData) -> Unit
) {
    Column(
        modifier = modifier
            .padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp
            )
    ) {
        SettingsItem(
            titleResId = R.string.fragment_settings_vibrate,
            hintResId = R.string.fragment_settings_vibrate_hint,
            isChecked = settingsData.isVibrationOnScan,
            clickListener = {
                onClickListener(
                    settingsData.copy(
                        isVibrationOnScan = it
                    )
                )
            }
        )
        Divider()
        SettingsItem(
            titleResId = R.string.fragment_settings_flash,
            hintResId = R.string.fragment_settings_flash_hint,
            isChecked = settingsData.isFlashlightWhenAppStarts,
            clickListener = {
                onClickListener(
                    settingsData.copy(
                        isFlashlightWhenAppStarts = it
                    )
                )
            }
        )
        Divider()
        SettingsItem(
            titleResId = R.string.fragment_settings_save_scanned_barcodes_to_history,
            hintResId = R.string.fragment_settings_save_scanned_barcodes_to_history_hint,
            isChecked = settingsData.isSaveScannedBarcodesToHistory,
            clickListener = {
                onClickListener(
                    settingsData.copy(
                        isSaveScannedBarcodesToHistory = it
                    )
                )
            }
        )
        Divider()
        SettingsItem(
            titleResId = R.string.fragment_settings_sending_note,
            hintResId = R.string.fragment_settings_sending_note_hint,
            isChecked = settingsData.isSendingNoteWithCode,
            clickListener = {
                onClickListener(
                    settingsData.copy(
                        isSendingNoteWithCode = it
                    )
                )
            }
        )
        Divider()
    }
}

@Composable
private fun SettingsItem(
    modifier: Modifier = Modifier,
    titleResId: Int,
    hintResId: Int,
    isChecked: Boolean,
    clickListener: ((Boolean) -> Unit)
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(text = stringResource(id = titleResId))
            Spacer(modifier = Modifier.height(4.dp))
            SecondaryText(text = stringResource(id = hintResId))
        }
        Switch(
            checked = isChecked,
            onCheckedChange = clickListener,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.primaryVariant,
                uncheckedThumbColor = MaterialTheme.colors.secondary
            )
        )
    }
}