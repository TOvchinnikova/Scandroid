package com.t_ovchinnikova.android.scandroid_2

import android.annotation.SuppressLint
import android.graphics.Rect
import android.graphics.RectF
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.core.graphics.toRect
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.t_ovchinnikova.android.scandroid_2.camera.CameraSource

lateinit var cropRect: Rect

class BarcodeAnalyzer : ImageAnalysis.Analyzer {

    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_QR_CODE,
            Barcode.FORMAT_AZTEC,
            Barcode.FORMAT_EAN_13)
        .build()

    //private val scanner = BarcodeScanning.getClient()

    fun onSuccess (image: InputImage) {

    }

    var boxRect: RectF? = null

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {

        if(boxRect != null) {
            cropRect = boxRect!!.toRect()
        } else {
            cropRect = Rect(0, 0, imageProxy.width, imageProxy.height)
        }

        val rotationDegrees = imageProxy.imageInfo.rotationDegrees


        val mediaImage = imageProxy.image

        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            val scanner = BarcodeScanning.getClient()

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    Log.d("MyLog", "Start!!!!!")
                    barcodes?.firstOrNull().let { barcode ->
                        val rawValue = barcode?.rawValue
                        rawValue?.let {
                            Log.d("MyLog", it)
                            //listener.onScanned(it)
                        }
                    }
                    /*for (barcode in barcodes) {
                        val bounds = barcode.boundingBox
                        val corners = barcode.cornerPoints

                        val rawValue = barcode.rawValue

                        val valueType = barcode.valueType
                        // See API reference for complete list of supported types
                        Log.d("MyLog", "$rawValue")
                        //Log.d("MyLog", "$barcode")
                       //Toast.makeText(context, rawValue, Toast.LENGTH_SHORT).show()
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
                    }*/
                    Log.d("MyLog", "GOOD!!!!!")
                }
                //Log.d("MyLog", "$barcodes")
                //Toast.makeText(requireContext(), barcodes.toString(), Toast.LENGTH_SHORT).show()
                .addOnFailureListener {
                    // Task failed with an exception
                    // ...
              //      Log.d("MyLog", "noGood")
                }
            imageProxy.close()
        }
    }

}