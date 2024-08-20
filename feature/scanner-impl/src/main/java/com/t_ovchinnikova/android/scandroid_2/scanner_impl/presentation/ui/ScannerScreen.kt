package com.t_ovchinnikova.android.scandroid_2.scanner_impl.presentation.ui

import android.annotation.SuppressLint
import android.util.Rational
import android.view.ViewGroup
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.Analyzer
import androidx.camera.core.UseCaseGroup
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.t_ovchinnikova.android.scandroid_2.core_ui.CenterProgress
import com.t_ovchinnikova.android.scandroid_2.core_utils.executor
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.presentation.model.mvi.ScannerScreenUiAction
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.presentation.model.mvi.ScannerScreenUiSideEffect
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.presentation.model.mvi.ScannerScreenUiState
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.viewmodel.ScanningViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel
import java.util.UUID
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import androidx.camera.core.Preview as CameraPreview

@Composable
fun ScannerScreen(
    paddingValues: PaddingValues,
    onScanListener: (codeId: UUID) -> Unit,
    viewModel: ScanningViewModel = koinViewModel<ScanningViewModel>()
) {
    val screenState = viewModel.uiState.collectAsState().value

    LaunchedEffect(key1 = Unit) {
        viewModel.uiSideEffect.onEach {
            when (it) {
                is ScannerScreenUiSideEffect.OpenCodeDetails -> {
                    onScanListener(it.codeId)
                }
            }
        }.launchIn(this)
    }
    ScannerContent(
        paddingValues = paddingValues,
        screenState = screenState,
        onAction = viewModel::onAction
    )
}

@Composable
private fun ScannerContent(
    paddingValues: PaddingValues,
    screenState: ScannerScreenUiState,
    onAction: (ScannerScreenUiAction) -> Unit
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
            val analyzer = get<Analyzer>()
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
            val previewUseCase = CameraPreview.Builder()
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
        ) { onAction(ScannerScreenUiAction.SwitchFlash) }
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
        .build()
}

@SuppressLint("UnsafeOptInUsageError")
private fun buildUseCaseGroup(preview: CameraPreview, imageAnalysis: ImageAnalysis): UseCaseGroup {
    return UseCaseGroup.Builder()
        .addUseCase(preview)
        .addUseCase(imageAnalysis)
        .build()
}