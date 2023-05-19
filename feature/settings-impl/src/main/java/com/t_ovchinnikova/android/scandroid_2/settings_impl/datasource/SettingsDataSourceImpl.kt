package com.t_ovchinnikova.android.scandroid_2.settings_impl.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import com.t_ovchinnikova.android.scandroid_2.settings_api.entity.SettingsData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsDataSourceImpl(
    private val dataStore: DataStore<Preferences>
) : SettingsDataSource {

    override fun getSettings(): Flow<SettingsData> {
        return dataStore.data.map { preferences ->
            SettingsData(
                isVibrationOnScan = preferences[VIBRATION_ON_SCAN_SETTINGS] ?: true,
                isSaveScannedBarcodesToHistory = preferences[SAVE_SCANNED_BARCODE_TO_HISTORY_SETTINGS]
                    ?: true,
                isFlashlightWhenAppStarts = preferences[FLASHLIGHT_WHEN_APP_STARTS_SETTINGS]
                    ?: false,
                isSendingNoteWithCode = preferences[SENDING_NOTE_WITH_CODE_SETTINGS] ?: false
            )
        }
    }

    override suspend fun saveSettings(settings: SettingsData) {
        dataStore.edit { preferences ->
            preferences[VIBRATION_ON_SCAN_SETTINGS] = settings.isVibrationOnScan
            preferences[SAVE_SCANNED_BARCODE_TO_HISTORY_SETTINGS] =
                settings.isSaveScannedBarcodesToHistory
            preferences[FLASHLIGHT_WHEN_APP_STARTS_SETTINGS] = settings.isFlashlightWhenAppStarts
            preferences[SENDING_NOTE_WITH_CODE_SETTINGS] = settings.isSendingNoteWithCode
        }
    }

    companion object {
        private const val SHARED_PREFERENCES_SETTINGS = "SHARED_PREFERENCES_SETTINGS"
        private const val SETTINGS_STORAGE = "SETTINGS_STORAGE"

        private val VIBRATION_ON_SCAN_SETTINGS = booleanPreferencesKey("vibrate")
        private val SAVE_SCANNED_BARCODE_TO_HISTORY_SETTINGS =
            booleanPreferencesKey("save_scanned_barcodes_to_history")
        private val FLASHLIGHT_WHEN_APP_STARTS_SETTINGS = booleanPreferencesKey("flash")
        private val SENDING_NOTE_WITH_CODE_SETTINGS = booleanPreferencesKey("sending note")

        internal fun Context.getSettingsDataStore(): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create(
                migrations = listOf(
                    SharedPreferencesMigration(this, SHARED_PREFERENCES_SETTINGS),
                ),
                produceFile = {
                    this.preferencesDataStoreFile(SETTINGS_STORAGE)
                }
            )
        }
    }
}