package com.t_ovchinnikova.android.scandroid_2.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.util.Rational
import android.util.Size
import android.view.LayoutInflater
import android.view.Surface.ROTATION_180
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.t_ovchinnikova.android.scandroid_2.camera.CameraSource
import com.t_ovchinnikova.android.scandroid_2.databinding.FragmentScannerBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class BarcodeScanningFragment : Fragment() {

    private lateinit var binding: FragmentScannerBinding
    private var requestPermissionLauncher : ActivityResultLauncher<String>? = null
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var cameraSource: CameraSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->0
                if (isGranted) {
                    cameraSource.startCamera()
                } else {
                    Toast.makeText(requireContext(),
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT).show()
                    //finish()
                }
            }

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentScannerBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.overlay.post {
            binding.overlay.setViewFinder()
        }

        cameraSource = CameraSource(binding.overlay)
        cameraSource.preview.setSurfaceProvider(binding.viewFinder.surfaceProvider)

    }

    override fun onStart() {
        super.onStart()

        if (allPermissionsGranted()) {
            cameraSource.startCamera()

        } else {
            requestPermissionLauncher?.launch(REQUIRED_PERMISSIONS)
        }

    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it.toString()) == PackageManager.PERMISSION_GRANTED

    }

    /*private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            //Используется для привязки жизненного цикла камер к владельцу жизненного цикла
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            bindCameraUseCases(cameraProvider)
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun bindCameraUseCases(cameraProvider : ProcessCameraProvider) {
        val preview : Preview = Preview.Builder()
            .build()

        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(Size(1280, 720))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_AZTEC,
                Barcode.FORMAT_EAN_13)
            .build()

        imageAnalysis.setAnalyzer(cameraExecutor, ImageAnalysis.Analyzer { image ->
            val rotationDegrees = image.imageInfo.rotationDegrees
            val scanner = BarcodeScanning.getClient(options)

            val mediaImage = image.image
            if (mediaImage != null) {
                val image1 = InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)
                // Pass image to an ML Kit Vision API
                // ...
                val result = scanner.process(image1)
                    .addOnSuccessListener { barcodes ->
                        for (barcode in barcodes) {
                            val bounds = barcode.boundingBox
                            val corners = barcode.cornerPoints

                            val rawValue = barcode.rawValue

                            val valueType = barcode.valueType
                            // See API reference for complete list of supported types
                            Log.d("MyLog", "$rawValue")
                            Log.d("MyLog", "$barcode")
                            Toast.makeText(requireContext(), rawValue, Toast.LENGTH_SHORT).show()
                            /*when (valueType) {
                                Barcode.TYPE_WIFI -> {
                                    val ssid = barcode.wifi!!.ssid
                                    val password = barcode.wifi!!.password
                                    val type = barcode.wifi!!.encryptionType
                                }
                                Barcode.TYPE_URL -> {
                                    val title = barcode.url!!.title
                                    val url = barcode.url!!.url
                                }
                            }*/
                        }
                    }
                //Log.d("MyLog", "$barcodes")
                //Toast.makeText(requireContext(), barcodes.toString(), Toast.LENGTH_SHORT).show()
                    .addOnFailureListener {
                        // Task failed with an exception
                        // ...
                    }
            }
            image.close()
        })

        var cameraSelector : CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        preview.setSurfaceProvider(binding.viewFinder.surfaceProvider)

        val viewPort =  ViewPort.Builder(Rational(1, 1), ROTATION_180).build()
        val useCaseGroup = UseCaseGroup.Builder()
            .addUseCase(preview)
            .addUseCase(imageAnalysis)
            //.addUseCase(imageCapture)
            //.setViewPort(viewPort)
            .build()

        cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, useCaseGroup)

    }*/


    companion object {
        fun newInstance() = BarcodeScanningFragment()

        const val REQUIRED_PERMISSIONS = Manifest.permission.CAMERA
    }

}