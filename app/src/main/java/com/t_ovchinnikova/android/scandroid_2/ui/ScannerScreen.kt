package com.t_ovchinnikova.android.scandroid_2.ui

import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.ui.theme.ColorScannerButtonPanel
import com.t_ovchinnikova.android.scandroid_2.utils.getCameraProvider
import com.t_ovchinnikova.android.scandroid_2.views.ViewFinderOverlay
import kotlinx.coroutines.launch

@androidx.compose.ui.tooling.preview.Preview
@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
) {
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        factory = { context ->
            val previewView = PreviewView(context).apply {
                this.scaleType = scaleType
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
            val previewUseCase = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            coroutineScope.launch {
                val cameraProvider = context.getCameraProvider()
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner, cameraSelector, previewUseCase
                    )
                } catch (ex: Exception) {
                    Log.e("CameraPreview", "Use case binding failed", ex)
                }
            }

            previewView
        }
    )
    AndroidView(
        factory = {
            ViewFinderOverlay(context = it, attrs = null)
        }
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 30.dp),
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
                onClick = { /*TODO добавить слушатель*/ }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_flash_on),
                    contentDescription = null
                )
            }
            IconButton(
                modifier = Modifier.size(70.dp),
                onClick = { /*TODO добавить слушатель*/ }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_image_analize),
                    contentDescription = null
                )
            }
        }
    }
}