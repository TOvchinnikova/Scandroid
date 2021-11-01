package com.t_ovchinnikova.android.scandroid_2.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.pm.PackageManager
import android.os.*
import android.util.DisplayMetrics
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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.t_ovchinnikova.android.scandroid_2.Settings
import com.t_ovchinnikova.android.scandroid_2.databinding.FragmentScanningBinding
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.t_ovchinnikova.android.scandroid_2.presentation.dialogs.ScanResultDialog
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


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

    private val settings: Settings by inject()

    private val viewModel by viewModel<ScanningViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()
        launchPermission()
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
        initView()
        setupViewModel()
        if (savedInstanceState == null) {
            viewModel.setScannerWorkState(true)
            viewModel.switchFlash(settings.flash)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun initView() {
        viewFinder = binding.viewFinder
        with(binding) {
            overlay.post {
                overlay.setViewFinder()
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
                viewFinder.post {
                    startCamera()
                }
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
        val display = viewFinder.display
        val screenAspectRatio = getScreenAspectRatio()
        val rotation = display.rotation
        val preview = buildPreview(screenAspectRatio, rotation)
        imageAnalysis = buildImageAnalysis(screenAspectRatio, rotation)
        val cameraSelector = buildCameraSelector()
        addOrientationEventListener(preview)
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

    private fun getScreenAspectRatio(): Int {
        val width: Int
        val height: Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val currentWindowMetrics = windowMetrics.currentWindowMetrics
            width = currentWindowMetrics.bounds.width()
            height = currentWindowMetrics.bounds.height()
        } else {
            val displayMetrics = DisplayMetrics()
            val display = viewFinder.display
            val metrics = displayMetrics.also { display.getRealMetrics(it) }
            width = metrics.widthPixels
            height = metrics.heightPixels
        }
        return Rational(width, height).toInt()
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

    private fun launchPermission() {
        requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    return@registerForActivityResult
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

    private fun isFlashAvailable() = requireContext().packageManager
        .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

    private fun vibrate() {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =  requireContext()
                .getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            requireContext().getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
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