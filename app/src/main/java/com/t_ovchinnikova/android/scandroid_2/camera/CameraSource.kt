package com.t_ovchinnikova.android.scandroid_2.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.util.Rational
import android.util.Size
import android.view.OrientationEventListener
import android.view.Surface
import android.view.Surface.ROTATION_0
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.t_ovchinnikova.android.scandroid_2.BarcodeAnalyzer
import com.t_ovchinnikova.android.scandroid_2.ui.ViewFinderOverlay
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CameraSource (private val overlay: ViewFinderOverlay) {

    private lateinit var cameraExecutor: ExecutorService

    val preview : Preview = Preview.Builder()
        .build()

    fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(overlay.context)

        cameraProviderFuture.addListener(Runnable {
            //Используется для привязки жизненного цикла камер к владельцу жизненного цикла
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            bindCameraUseCases(cameraProvider)
        }, ContextCompat.getMainExecutor(overlay.context))
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun bindCameraUseCases(cameraProvider: ProcessCameraProvider) {
       // Log.d("MyLog", "bindCameraUseCases!!!!!")
        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(Size(overlay.width, overlay.height))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        val orientationEventListener = object : OrientationEventListener(overlay.context) {
            override fun onOrientationChanged(orientation : Int) {
                // Monitors orientation values to determine the target rotation value
                val rotation : Int = when (orientation) {
                    in 45..134 -> Surface.ROTATION_270
                    in 135..224 -> Surface.ROTATION_180
                    in 225..314 -> Surface.ROTATION_90
                    else -> Surface.ROTATION_0
                }

                imageAnalysis.targetRotation = rotation
            }
        }
        orientationEventListener.enable()

        cameraExecutor = Executors.newSingleThreadExecutor()

        val analyzer = BarcodeAnalyzer()

       analyzer.boxRect = overlay.boxRect

        imageAnalysis.setAnalyzer(cameraExecutor, analyzer)

        var cameraSelector : CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        var viewPort: ViewPort

       /* viewPort = if(overlay.boxRect != null) {
            Log.d("MyLog", "Yes")
            ViewPort.Builder(Rational(overlay.boxRect!!.width().toInt(), overlay.boxRect!!.height().toInt()), ROTATION_0).build()
        } else {
            Log.d("MyLog", "No")
            ViewPort.Builder(Rational(1, 1), ROTATION_0).build()
        }*/


        val useCaseGroup = UseCaseGroup.Builder()
            .addUseCase(preview)
            .addUseCase(imageAnalysis)
            //.addUseCase(imageCapture)
            //.setViewPort(viewPort)
            .build()

        cameraProvider.bindToLifecycle(overlay.context as LifecycleOwner, cameraSelector, useCaseGroup)
    }
}