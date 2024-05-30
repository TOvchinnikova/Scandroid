package com.t_ovchinnikova.android.scandroid_2.scanner_impl.ui

import android.annotation.SuppressLint
import android.util.Rational
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.camera.core.ImageAnalysis.Analyzer
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_ui.CenterProgress
import com.t_ovchinnikova.android.scandroid_2.core_utils.executor
import com.t_ovchinnikova.android.scandroid_2.core_utils.vibrate
import com.t_ovchinnikova.android.scandroid_2.scanner_api.ScanResultListener
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.viewmodel.ScanningViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.util.*
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun ScannerScreen(
    paddingValues: PaddingValues,
    onScanListener: (codeId: UUID) -> Unit,
    viewModel: ScanningViewModel = koinViewModel<ScanningViewModel>()
) {
    val screenState = viewModel.uiState.collectAsState().value

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.uiSideEffect.onEach {
            when (it) {
                ScannerScreenUiSideEffect.Vibrate -> {
                    context.vibrate()
                }
            }
        }.launchIn(this)
    }

    val previewCameraView = remember {
        PreviewView(context).apply {
            this.scaleType = scaleType
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }
    val camera: MutableState<Camera?> = remember {
        mutableStateOf(null)
    }
    val cameraProvider: MutableState<ProcessCameraProvider?> = remember {
        mutableStateOf(null)
    }

    val configuration = LocalConfiguration.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraSelector = remember { buildCameraSelector() }
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    val imageAnalysis = remember {
        buildImageAnalysis(
            Rational(
                configuration.screenHeightDp,
                configuration.screenHeightDp
            ).toInt()
        )
    }

    Box(modifier = Modifier.padding(paddingValues)) {
        AndroidView(factory = { previewCameraView })
        AndroidView(factory = { ViewFinderOverlay(context = it, attrs = null) })

        if (screenState.isSavingCode) {
            CenterProgress()
            imageAnalysis.clearAnalyzer()
            cameraProvider.value?.unbindAll()
        } else {
            val analyzer = get<Analyzer> {
                parametersOf(
                    object : ScanResultListener {
                        override fun onScanned(resultCode: Code) {
                            if (screenState.lastScannedCode?.text != resultCode.text) {
                                viewModel.onAction(ScannerScreenUiAction.CodeScanned(resultCode))
                                onScanListener.invoke(resultCode.id)
                            }
                        }
                    }, 74, 20
                )
            }
            imageAnalysis.setAnalyzer(cameraExecutor, analyzer)
        }
        LaunchedEffect(cameraSelector) {
            cameraProvider.value = suspendCoroutine<ProcessCameraProvider> { continuation ->
                ProcessCameraProvider.getInstance(context).also { future ->
                    future.addListener({
                        continuation.resume(future.get())
                    }, context.executor)
                }
            }
            val previewUseCase = Preview.Builder()
                .build()
                .also { it.setSurfaceProvider(previewCameraView.surfaceProvider) }

            runCatching {
                cameraProvider.value?.unbindAll()
                camera.value = cameraProvider.value?.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    buildUseCaseGroup(previewUseCase, imageAnalysis)
                )
            }
        }
        CameraButtonPanel(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp),
            isFlashing = screenState.isFlashlightWorks
        ) { viewModel.onAction(ScannerScreenUiAction.SwitchFlash) }
        camera.value?.cameraControl?.enableTorch(screenState.isFlashlightWorks)
    }
    if (screenState.isLoading) {
        CenterProgress()
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
        //.setTargetRotation(rotation)
        .build()
}

@SuppressLint("UnsafeOptInUsageError")
private fun buildUseCaseGroup(preview: Preview, imageAnalysis: ImageAnalysis): UseCaseGroup {
    return UseCaseGroup.Builder()
        .addUseCase(preview)
        .addUseCase(imageAnalysis)
        .build()
}