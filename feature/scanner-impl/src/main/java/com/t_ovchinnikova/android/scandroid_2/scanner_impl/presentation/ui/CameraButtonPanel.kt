package com.t_ovchinnikova.android.scandroid_2.scanner_impl.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.t_ovchinnikova.android.scandroid_2.core_ui.theme.ColorScannerButtonPanel
import com.t_ovchinnikova.android.scandroid_2.core_ui.theme.ScandroidTheme
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.R

@Composable
fun CameraButtonPanel(
    modifier: Modifier = Modifier,
    isFlashing: Boolean,
    flashClickListener: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier.background(
                color = ColorScannerButtonPanel.copy(alpha = 0.7f),
                shape = RoundedCornerShape(10.dp)
            ),
        ) {
            IconButton(
                modifier = Modifier.size(70.dp),
                onClick = { flashClickListener() }
            ) {
                Image(
                    painter = painterResource(
                        id = if (isFlashing) {
                            R.drawable.ic_flash_on
                        }
                        else {
                            R.drawable.ic_flash_off
                        }
                    ),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
fun CameraButtonPanelPreview() {
    ScandroidTheme {
        CameraButtonPanel(isFlashing = true, flashClickListener= {})
    }
}

@Preview
@Composable
fun CameraButtonPanelPreviewDark() {
    ScandroidTheme(true) {
        CameraButtonPanel(isFlashing = true, flashClickListener= {})
    }
}