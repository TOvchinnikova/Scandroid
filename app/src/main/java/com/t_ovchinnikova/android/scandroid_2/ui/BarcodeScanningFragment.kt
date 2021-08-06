package com.t_ovchinnikova.android.scandroid_2.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.*
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.Rational
import android.view.*
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.core.Camera
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.t_ovchinnikova.android.scandroid_2.BarcodeAnalyzer
import com.t_ovchinnikova.android.scandroid_2.camera.CameraSource
import com.t_ovchinnikova.android.scandroid_2.databinding.FragmentScannerBinding
import kotlinx.android.synthetic.main.bottom_action_bar_scanning.view.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.properties.Delegates

class BarcodeScanningFragment : Fragment() {

    private lateinit var binding: FragmentScannerBinding
    private lateinit var requestPermissionLauncher : ActivityResultLauncher<String>
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var viewFinder: PreviewView
    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var flashButton: ImageButton
    private lateinit var camera: Camera
    private var flashEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cameraExecutor = Executors.newSingleThreadExecutor()

        requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->0
                if (isGranted) {
                    Log.d("MyLog", "allPermissionsGranted why")
                    startCamera()
                } else {
                    Toast.makeText(requireContext(),
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT).show()
                    //finish()
                }
            }
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
        Log.d("MyLog", "onViewCreated start")

        binding.overlay.post {
            binding.overlay.setViewFinder()
        }

        binding.container.imageAnalizeButton.setOnClickListener{
            Toast.makeText(requireContext(), "supa dupa!!!", Toast.LENGTH_SHORT).show()
        }

        viewFinder = binding.viewFinder

        requestPermissionLauncher.launch(Manifest.permission.CAMERA)

        flashButton = binding.container.flashButton
       // if (camera.cameraInfo.hasFlashUnit()) {

            //flashButton.visibility = View.VISIBLE

            flashButton.setOnClickListener{
                toggleFlash()
            //}

            /*camera.cameraInfo.torchState.observe(this@BarcodeScanningFragment) {
                it?.let { torchState ->
                    if (torchState == TorchState.ON) {
                        flashEnabled = true
                        binding.ivFlashControl.setImageResource(R.drawable.ic_round_flash_on)
                    } else {
                        flashEnabled = false
                        binding.ivFlashControl.setImageResource(R.drawable.ic_round_flash_off)
                    }
                }
            }*/

        }
        Log.d("MyLog", "onViewCreated finish")

    }

    private fun checkPreviewStreamState(){

    }

    override fun onStart() {
        super.onStart()


    }

    override fun onDestroy() {
        super.onDestroy()

        cameraExecutor.shutdown()
    }

    private fun toggleFlash() {

        flashButton.isActivated = flashButton.isActivated.not()

        if (camera.cameraInfo.torchState.value == TorchState.ON) {
            camera.cameraControl.enableTorch(false)
        } else {
            camera.cameraControl.enableTorch(true)
        }

    }

   /* private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        Log.d("MyLog", "allPermissionsGranted start")
        ContextCompat.checkSelfPermission(
            requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }*/

    private fun startCamera() {
        Log.d("MyLog", "startCamera start")
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            cameraProvider = cameraProviderFuture.get()
            bindCameraUseCases(cameraProvider)
        }, ContextCompat.getMainExecutor(requireContext()))
        Log.d("MyLog", "startCamera finish")
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun bindCameraUseCases(cameraProvider: ProcessCameraProvider) {
        Log.d("MyLog", "bindCameraUseCases start")
        val metrics = DisplayMetrics().also { viewFinder.display.getRealMetrics(it) }
        val screenAspectRatio = Rational(metrics.widthPixels, metrics.heightPixels).toInt()
        val rotation = viewFinder.display.rotation

        val preview = Preview.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()
            .also {
                it.setSurfaceProvider(viewFinder.surfaceProvider)
            }

        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .setTargetRotation(rotation)
            .build()

        var cameraSelector : CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        val orientationEventListener = object : OrientationEventListener(requireContext()) {
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

        val analyzer = BarcodeAnalyzer()
        imageAnalysis.setAnalyzer(cameraExecutor, analyzer)

        val useCaseGroup = UseCaseGroup.Builder()
            .addUseCase(preview)
            .addUseCase(imageAnalysis)
            .build()

        cameraProvider.unbindAll()
        camera = cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, useCaseGroup)

        if (camera.cameraInfo.hasFlashUnit()) {
            flashButton.visibility = View.VISIBLE
        }
    }

    companion object {

        fun newInstance() = BarcodeScanningFragment()
        //private val REQUIRED_PERMISSIONS = Manifest.permission.CAMERA

    }

}