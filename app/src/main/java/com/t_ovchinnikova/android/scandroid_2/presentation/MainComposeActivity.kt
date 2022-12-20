package com.t_ovchinnikova.android.scandroid_2.presentation

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import com.t_ovchinnikova.android.scandroid_2.ui.scanner.CameraPreview
import com.t_ovchinnikova.android.scandroid_2.ui.settings.SettingsScreen
import com.t_ovchinnikova.android.scandroid_2.ui.theme.ScandroidTheme

class MainComposeActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            return@registerForActivityResult
        } else {
            Toast.makeText(
                this,
                "Permissions not granted by the user.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissionLauncher.launch(Manifest.permission.CAMERA)

        setContent {
            ScandroidTheme {
                CameraPreview()
            }
        }
    }
}