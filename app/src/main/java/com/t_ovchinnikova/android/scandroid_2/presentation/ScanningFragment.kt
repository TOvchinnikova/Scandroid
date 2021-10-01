package com.t_ovchinnikova.android.scandroid_2.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.DisplayMetrics
import android.util.Log
import android.util.Rational
import android.view.*
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.t_ovchinnikova.android.scandroid_2.ScanAnalyzer
import com.t_ovchinnikova.android.scandroid_2.Settings
import com.t_ovchinnikova.android.scandroid_2.databinding.FragmentScanningBinding
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class ScanningFragment : Fragment() {

    private lateinit var binding: FragmentScanningBinding
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var viewFinder: PreviewView
    private var cameraProvider: ProcessCameraProvider? = null
    private var imageAnalysis: ImageAnalysis? = null
    private lateinit var flashButton: ImageButton
    private lateinit var camera: Camera
    private var flashState: Boolean = false
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private var newCode: Code? = null
    private lateinit var settings: Settings

    private val viewModel by viewModels<ScanningViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()
        settings = Settings(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScanningBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchPermission()
        setupViewModel()
        if (savedInstanceState == null) {
            viewModel.setScannerWorkState(true)
            viewModel.switchFlash(settings.flash)
        }
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun launchPermission() {
        requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    setupViewModel()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun initView() {
        viewFinder = binding.viewFinder
        with(binding) {
            overlay.post {
                overlay.setViewFinder()
            }
            bottomActionBar.imageAnalizeButton.setOnClickListener {
                Toast.makeText(requireContext(), "supa dupa!!!", Toast.LENGTH_SHORT).show()
            }
            flashButton = bottomActionBar.flashButton
        }
        if (isFlashAvailable()) {
            flashButton.visibility = View.VISIBLE
            flashButton.setOnClickListener {
                viewModel.switchFlash(flashState.not())
                toggleFlash()
            }
        }
    }

    private fun setupViewModel() {
        viewModel.scannerWorkState.observe(viewLifecycleOwner) {
            if (it) {
                startCamera()
            } else {
                stopCamera()
                newCode = null
            }
        }
        viewModel.flashState.observe(viewLifecycleOwner) {
            flashState = it
        }
        viewModel.newCode.observe(viewLifecycleOwner) {
            if (newCode == null) {
                it?.let {
                    binding.scanProgress.visibility = View.GONE
                    newCode = it
                    showScanResultDialog(it)
                }
            }
        }
    }

    private fun showScanResultDialog(code: Code) {
        ScanResultDialog.newInstance(code)
            .show(childFragmentManager, ScanResultDialog::class.java.simpleName)
    }

    private fun toggleFlash() {
        flashButton.isActivated = flashState
        camera.cameraControl.enableTorch(flashState)
    }

    private fun stopCamera() {
        imageAnalysis?.clearAnalyzer()
        cameraProvider?.unbindAll()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(
            {
                cameraProvider = cameraProviderFuture.get()
                cameraProvider?.let {
                    bindCameraUseCases(it)
                }
            },
            ContextCompat.getMainExecutor(requireContext())
        )
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun bindCameraUseCases(cameraProvider: ProcessCameraProvider) {
        val metrics = DisplayMetrics().also { viewFinder.display.getRealMetrics(it) }
        val screenAspectRatio = Rational(metrics.widthPixels, metrics.heightPixels).toInt()
        val rotation = viewFinder.display.rotation
        val preview = buildPreview(screenAspectRatio, rotation)
        imageAnalysis = buildImageAnalysis(screenAspectRatio, rotation)
        val cameraSelector = buildCameraSelector()
        addOrientationEventListener(preview)
        cameraExecutor = Executors.newSingleThreadExecutor()
        val scanListener = ScanListener()
        val analyzer = ScanAnalyzer(scanListener)
        imageAnalysis?.setAnalyzer(cameraExecutor, analyzer)
        val useCaseGroup = buildUseCaseGroup(preview)
        cameraProvider.unbindAll()
        camera = cameraProvider
            .bindToLifecycle(this as LifecycleOwner, cameraSelector, useCaseGroup)
        if (camera.cameraInfo.hasFlashUnit()) {
            flashButton.visibility = View.VISIBLE
            toggleFlash()
        }
    }

    private fun buildPreview(screenAspectRatio: Int, rotation: Int): Preview {
        return Preview.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()
            .also {
                it.setSurfaceProvider(viewFinder.surfaceProvider)
            }
    }

    private fun buildImageAnalysis(screenAspectRatio: Int, rotation: Int): ImageAnalysis {
        return ImageAnalysis.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .setTargetRotation(rotation)
            .build()
    }

    private fun buildCameraSelector(): CameraSelector {
        return CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun buildUseCaseGroup(preview: Preview): UseCaseGroup {
        return UseCaseGroup.Builder()
            .addUseCase(preview)
            .addUseCase(
                imageAnalysis ?: throw IllegalStateException("imageAnalysis must be initialized")
            )
            .build()
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun addOrientationEventListener(preview: Preview) {
        val orientationEventListener = object : OrientationEventListener(requireContext()) {
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

    private fun isFlashAvailable() = requireContext().packageManager
        .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

    private fun vibrate() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(350, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(350)
       }
    }

    inner class ScanListener : ScanResultListener {

        override fun onScanned(resultCode: Code) {
            if (settings.vibrate) vibrate()
            binding.scanProgress.visibility = View.VISIBLE
            viewModel.addCode(resultCode, settings.saveScannedBarcodesToHistory)
            viewModel.setScannerWorkState(false)
        }
    }

    companion object {
        fun newInstance() = ScanningFragment()
    }
}