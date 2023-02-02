package com.t_ovchinnikova.android.scandroid_2.ui.scanner

import android.content.Context
import android.util.Log
import android.view.OrientationEventListener
import android.view.Surface
import android.view.ViewGroup
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.presentation.ScanResultListener
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.ScanningViewModel
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.SettingsViewModel
import com.t_ovchinnikova.android.scandroid_2.ui.theme.ColorScannerButtonPanel
import com.t_ovchinnikova.android.scandroid_2.utils.executor
import com.t_ovchinnikova.android.scandroid_2.utils.getCameraProvider
import com.t_ovchinnikova.android.scandroid_2.utils.vibrate
import com.t_ovchinnikova.android.scandroid_2.views.ViewFinderOverlay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.get
import org.koin.androidx.compose.inject
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    context: Context
) {
    val viewModel = koinViewModel<ScanningViewModel>()

    val coroutineScope = rememberCoroutineScope()
    val previewCameraView = remember { PreviewView(context).apply {
        this.scaleType = scaleType
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    } }
    val cameraProviderFuture =
        remember(context) { ProcessCameraProvider.getInstance(context) }
    val cameraProvider = remember(cameraProviderFuture) { cameraProviderFuture.get() }
    val lifecycleOwner = LocalLifecycleOwner.current
    val camera: Camera? = null
    val analyzer = get<ImageAnalysis.Analyzer> {
        parametersOf(
            object : ScanResultListener {
                override fun onScanned(resultCode: Code) {
                    if (viewModel.lastScannedCode.value?.text != resultCode.text) {
                        val settings = viewModel.getSettings()
                        if (settings?.isVibrationOnScan == true) context.vibrate()
                        viewModel.addCode(resultCode)
                    }
                }
            },
            20,
            74
        )
    }
    var imageAnalysis: ImageAnalysis? = null
    AndroidView(
        factory = {
            cameraProviderFuture.addListener(
                {
                    val screenAspectRatio = getScreenAspectRatio()
                    val rotation = display.rotation
                    imageAnalysis = buildImageAnalysis(screenAspectRatio, rotation)
                    //addOrientationEventListener(preview)
                    imageAnalysis?.setAnalyzer(cameraExecutor, analyzer)


                    val cameraSelector = buildCameraSelector()
                    cameraProvider.unbindAll()

                    val previewUseCase = Preview.Builder()
                        .build()
                        .also {
                            it.setSurfaceProvider(previewCameraView.surfaceProvider)
                        }

                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        previewUseCase
                    )
                }, context.executor
            )

            previewCameraView
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

private fun buildCameraSelector(): CameraSelector {
    return CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()
}

private fun buildImageAnalysis(screenAspectRatio: Int, rotation: Int): ImageAnalysis {
    return ImageAnalysis.Builder()
        .setTargetAspectRatio(screenAspectRatio)
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .setTargetRotation(rotation)
        .build()
}

private fun addOrientationEventListener(preview: Preview) {
    val orientationEventListener = object : OrientationEventListener(()) {
        override fun onOrientationChanged(orientation: Int) {
            val rotation: Int = when (orientation) {
                in 45..134 -> Surface.ROTATION_270
                in 135..224 -> Surface.ROTATION_180
                in 225..314 -> Surface.ROTATION_90
                else -> Surface.ROTATION_0
            }
            preview.targetRotation = rotation
            imageAnalysis?.targetRotation = rotation
        }
    }
    orientationEventListener.enable()
}