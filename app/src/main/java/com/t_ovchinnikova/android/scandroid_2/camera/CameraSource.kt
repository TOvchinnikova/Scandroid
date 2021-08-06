package com.t_ovchinnikova.android.scandroid_2.camera

import android.annotation.SuppressLint
import android.content.Context
import android.util.DisplayMetrics
import android.util.Rational
import android.view.OrientationEventListener
import android.view.Surface
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.t_ovchinnikova.android.scandroid_2.ScanAnalyzer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


/*class CameraSource {

    private lateinit var cameraExecutor: ExecutorService

    val preview = Preview.Builder()
        .build()

    fun startCamera(context: Context, metrics: DisplayMetrics, rotation: Int) {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener(Runnable {
            //Используется для привязки жизненного цикла камер к владельцу жизненного цикла
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            bindCameraUseCases(cameraProvider, context, metrics, rotation)
        }, ContextCompat.getMainExecutor(context))
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun bindCameraUseCases(cameraProvider: ProcessCameraProvider, context: Context, metrics: DisplayMetrics, rotation: Int) {

        val screenAspectRatio = Rational(metrics.widthPixels, metrics.heightPixels).toInt()

        preview.targetRotation = rotation //= Preview.Builder()
        //preview.setTargetAspectRatio(screenAspectRatio)
        //.setTargetRotation(rotation)
        //.build()

        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .setTargetRotation(rotation)
            .build()

        var cameraSelector : CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        val orientationEventListener = object : OrientationEventListener(context) {
            override fun onOrientationChanged(orientation : Int) {
                val rotation : Int = when (orientation) {
                    in 45..134 -> Surface.ROTATION_270
                    in 135..224 -> Surface.ROTATION_180
                    in 225..314 -> Surface.ROTATION_90
                    else -> Surface.ROTATION_0
                }
                preview.targetRotation = rotation
                imageAnalysis.targetRotation = rotation
            }
        }

        orientationEventListener.enable()

        cameraExecutor = Executors.newSingleThreadExecutor()

        val analyzer = ScanAnalyzer()

        imageAnalysis.setAnalyzer(cameraExecutor, analyzer)

        val useCaseGroup = UseCaseGroup.Builder()
            .addUseCase(preview!!)
            .addUseCase(imageAnalysis)
            .build()

        cameraProvider.bindToLifecycle(context as LifecycleOwner, cameraSelector, useCaseGroup)
    }

}*/