package com.t_ovchinnikova.android.scandroid_2.ui.scanner

import android.annotation.SuppressLint
import android.util.Rational
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.camera.core.ImageAnalysis.Analyzer
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
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
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun CameraPreview(
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<ScanningViewModel>()
    val screenState = viewModel.screenStateFlow.collectAsState()

    when (val currentState = screenState.value) {
        is ScannerScreenState.Scanning -> {
            Scanner(viewModel, currentState)
        }
        is ScannerScreenState.SavingCode -> {

        }
        is ScannerScreenState.Paused -> {

        }
        is ScannerScreenState.Initial -> {

        }
    }
}

@Composable
fun Scanner(
    viewModel: ScanningViewModel,
    scannerState: ScannerScreenState.Scanning
) {
    val context = LocalContext.current
    val previewCameraView = remember {
        PreviewView(context).apply {
            this.scaleType = scaleType
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    val configuration = LocalConfiguration.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraSelector = remember { buildCameraSelector() }
    val executor = context.executor
    val cameraExecutor =  remember { Executors.newSingleThreadExecutor() }
    var camera: Camera? = null
    val analyzer = get<Analyzer> {
        parametersOf(
            object : ScanResultListener {
                override fun onScanned(resultCode: Code) {
                    if (scannerState.lastScannedCode?.text != resultCode.text) {
                        if (scannerState.settingsData.isVibrationOnScan) context.vibrate()
                        viewModel.saveCode(resultCode)
                    }
                }
            },
            74,
            20
        )
    }

    val imageAnalysis: ImageAnalysis = remember {
        buildImageAnalysis(Rational(configuration.screenHeightDp, configuration.screenHeightDp).toInt()).apply {
            setAnalyzer(cameraExecutor, analyzer)
        }
    }

    LaunchedEffect(cameraSelector) {
        val cameraProvider = suspendCoroutine<ProcessCameraProvider> { continuation ->
            ProcessCameraProvider.getInstance(context).also { future ->
                future.addListener({
                    continuation.resume(future.get())
                }, executor)
            }
        }

        val previewUseCase = Preview.Builder()
            .build()
            .also { it.setSurfaceProvider(previewCameraView.surfaceProvider) }

        camera = runCatching {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                buildUseCaseGroup(previewUseCase, imageAnalysis)
            )
        }.getOrNull()
    }
camera?.cameraControl?.enableTorch(scannerState.isFlashlightWorks)
    AndroidView(factory = { previewCameraView })
    AndroidView(factory = { ViewFinderOverlay(context = it, attrs = null) })
    CameraButtonPanel (scannerState.settingsData.isFlashlightWhenAppStarts) { viewModel.switchFlash() }
}

@Composable
fun CameraButtonPanel(
    isFlashing: Boolean,
    flashClickListener: () -> Unit
) {
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