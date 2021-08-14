package com.t_ovchinnikova.android.scandroid_2.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
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
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.t_ovchinnikova.android.scandroid_2.ScanAnalyzer
import com.t_ovchinnikova.android.scandroid_2.ScanResultListener
import com.t_ovchinnikova.android.scandroid_2.databinding.FragmentScannerBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class ScanningFragment : Fragment() {

    private lateinit var binding: FragmentScannerBinding
  //  private lateinit var requestPermissionLauncher : ActivityResultLauncher<String>
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var viewFinder: PreviewView
    private var cameraProvider: ProcessCameraProvider? = null
    private var imageAnalysis: ImageAnalysis? = null
    private lateinit var flashButton: ImageButton
    private lateinit var camera: Camera

    private val viewModel by viewModels<ScanningViewModel>()

    //private var viewModel: ScanningViewModel? = null

   /* private val viewModel: ScanningViewModel by lazy {
        ViewModelProvider(this).get(ScanningViewModel::class.java)
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cameraExecutor = Executors.newSingleThreadExecutor()

     /*   requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    //viewModel.setScannerWorkState(true)//
                    startCamera()
                } else {
                    Toast.makeText(requireContext(),
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT).show()
                    //finish()
                }
            }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentScannerBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModel()

        if (savedInstanceState == null) viewModel.setScannerWorkState(true)

        //requestPermissionLauncher.launch(Manifest.permission.CAMERA)

        binding.overlay.post {
            binding.overlay.setViewFinder()
        }

        binding.bottomActionBar.imageAnalizeButton.setOnClickListener{
            Toast.makeText(requireContext(), "supa dupa!!!", Toast.LENGTH_SHORT).show()
        }

        viewFinder = binding.viewFinder

        flashButton = binding.bottomActionBar.flashButton

        if(isFlashAvailable()) {
            flashButton.visibility = View.VISIBLE

            flashButton.setOnClickListener {
                toggleFlash()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        cameraExecutor.shutdown()
    }

    private fun setUpViewModel() {
        //viewModel = ViewModelProvider(this).get(ScanningViewModel::class.java)

        viewModel.scannerWorkState.observe(viewLifecycleOwner, Observer {
            Log.d("MyLog", "Observer")
            if (it) {
                startCamera()
            } else {
                Log.d("MyLog", "Stop")
                stopCamera()
            }
        })
    }

    fun setScannerWorkState(state: Boolean) {
        viewModel.setScannerWorkState(state)
    }

    private fun toggleFlash() {

        flashButton.isActivated = flashButton.isActivated.not()

        if (camera.cameraInfo.torchState.value == TorchState.ON) {
            camera.cameraControl.enableTorch(false)
        } else {
            camera.cameraControl.enableTorch(true)
        }
    }

    private fun stopCamera() {
        imageAnalysis?.clearAnalyzer()
        cameraProvider?.unbindAll()
    }

    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
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

        val preview = Preview.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()
            .also {
                it.setSurfaceProvider(viewFinder.surfaceProvider)
            }

        imageAnalysis = ImageAnalysis.Builder()
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
                imageAnalysis?.targetRotation = rotation
            }
        }

        orientationEventListener.enable()

        cameraExecutor = Executors.newSingleThreadExecutor()

        /*class ScanListener : ScanResultListener{
            override fun onScanned(result: String) {
                imageAnalysis.clearAnalyzer()
                cameraProvider.unbindAll()
                ScanResultDialog.showScanResult(result, parentFragmentManager)
            }
        }*/
        val scanListener =  ScanListener()

        val analyzer = ScanAnalyzer(scanListener)
        imageAnalysis?.setAnalyzer(cameraExecutor, analyzer)

        val useCaseGroup = UseCaseGroup.Builder()
            .addUseCase(preview)
            .addUseCase(imageAnalysis ?: throw IllegalStateException("imageAnalysis must be initialized"))
            .build()

        cameraProvider.unbindAll()
        camera = cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, useCaseGroup)

        if (camera.cameraInfo.hasFlashUnit()) {
            flashButton.visibility = View.VISIBLE
        }
    }

    private fun isFlashAvailable() = requireContext().packageManager
        .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

    inner class ScanListener : ScanResultListener{
        override fun onScanned(result: String) {
            //imageAnalysis.clearAnalyzer()
            //cameraProvider.unbindAll()
            viewModel.setScannerWorkState(false)
            ScanResultDialog.showScanResult(result, parentFragmentManager)
        }
    }

    companion object {

        fun newInstance() = ScanningFragment()

    }
}