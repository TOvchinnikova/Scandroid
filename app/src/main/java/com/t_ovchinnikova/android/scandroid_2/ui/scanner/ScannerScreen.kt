package com.t_ovchinnikova.android.scandroid_2.ui.scanner

import android.annotation.SuppressLint
import android.content.Context
import android.util.Rational
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.canhub.cropper.CropImage.CancelledResult.rotation
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.presentation.ScanResultListener
import com.t_ovchinnikova.android.scandroid_2.presentation.viewmodel.ScanningViewModel
import com.t_ovchinnikova.android.scandroid_2.ui.theme.ColorScannerButtonPanel
import com.t_ovchinnikova.android.scandroid_2.utils.executor
import com.t_ovchinnikova.android.scandroid_2.utils.vibrate
import com.t_ovchinnikova.android.scandroid_2.views.ViewFinderOverlay
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun CameraPreview(
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<ScanningViewModel>()
    val context = LocalContext.current

    val previewCameraView = remember { PreviewView(context).apply {
        this.scaleType = scaleType
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    } }
    val cameraProviderFuture =
        remember(context) { ProcessCameraProvider.getInstance(context) }

    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraSelector = remember { buildCameraSelector() }
    val cameraExecutor = context.executor
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

        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp
        val screenWidth = configuration.screenWidthDp

    val imageAnalysis: ImageAnalysis = remember {
        buildImageAnalysis(Rational(screenWidth, screenHeight).toInt()).apply {
            setAnalyzer(cameraExecutor, analyzer)
        }
    }

    LaunchedEffect(cameraSelector) {
        val cameraProvider = suspendCoroutine<ProcessCameraProvider> { continuation ->
            ProcessCameraProvider.getInstance(context).also { future ->
                future.addListener({
                    continuation.resume(future.get())
                }, cameraExecutor)
            }
        }

        val previewUseCase = Preview.Builder()
            .build()
            .also { it.setSurfaceProvider(previewCameraView.surfaceProvider) }

        runCatching {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                buildUseCaseGroup(previewUseCase, imageAnalysis)
            )
        }
    }

    AndroidView(factory = { previewCameraView })
    AndroidView(factory = { ViewFinderOverlay(context = it, attrs = null) })
    CameraButtonPanel()

}

@Composable
fun CameraButtonPanel() {
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

private fun buildImageAnalysis(aspectRatio: Int): ImageAnalysis {
    return ImageAnalysis.Builder()
        .setTargetAspectRatio(aspectRatio)
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .setTargetRotation(rotation)
        .build()
}

@SuppressLint("UnsafeOptInUsageError")
private fun buildUseCaseGroup(preview: Preview, imageAnalysis: ImageAnalysis): UseCaseGroup {
    return UseCaseGroup.Builder()
        .addUseCase(preview)
        .addUseCase(imageAnalysis)
        .build()
}