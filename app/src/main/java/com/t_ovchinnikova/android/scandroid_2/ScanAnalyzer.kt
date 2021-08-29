package com.t_ovchinnikova.android.scandroid_2

import android.annotation.SuppressLint
import android.graphics.*
import android.media.Image
import android.util.Log
import androidx.annotation.ColorInt
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.t_ovchinnikova.android.scandroid_2.data.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.domain.Code


class ScanAnalyzer(private val listener: ScanResultListener) : ImageAnalysis.Analyzer {

    private val CHANNEL_RANGE = 0 until (1 shl 18)
    private val scanner = BarcodeScanning.getClient()
    private val codeRepository = CodeRepository.get()

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            checkHolderDrawState(imageProxy, mediaImage)
        }
    }

    private fun recognizeCode(image: InputImage): Task<List<Barcode>> =
        scanner.process(image)
            .addOnSuccessListener {
        if (it.isNotEmpty()) checkList(it)
    }

    private fun checkList(list: List<Barcode>) {
        list.firstOrNull().let { barcode ->
            val rawValue = barcode?.rawValue
            rawValue?.let {
                val format = barcode.format
                val type = barcode.valueType
                val code = Code(text = rawValue, format = format, type = type)
                codeRepository.addCode(code)
                //TODO необходимо знать id записи, чтобы в открывшемся окне можно было удалить запись по id
                listener.onScanned(code)
            }
        }
    }

    private fun checkHolderDrawState(image: ImageProxy, mediaImage: Image) {

        val rotationDegrees = image.imageInfo.rotationDegrees

        val imageHeight = mediaImage.height
        val imageWidth = mediaImage.width

        val convertImageToBitmap = mediaImage.convertYuv420888ImageToBitmap()

        val cropRect = Rect(0, 0, imageWidth, imageHeight)

        val heightCropPercent = 74
        val widthCropPercent = 20
        val (widthCrop, heightCrop) = when (rotationDegrees) {
            90, 270 -> Pair(heightCropPercent / 100f, widthCropPercent / 100f)
            else -> Pair(widthCropPercent / 100f, heightCropPercent / 100f)
        }

        cropRect.inset(
            (imageWidth * widthCrop / 2).toInt(),
            (imageHeight * heightCrop / 2).toInt()
        )

        if (cropRect.height() > 0 && cropRect.width() > 0) {
            val croppedBitmap = rotateAndCrop(convertImageToBitmap, rotationDegrees, cropRect)
            recognizeCode(InputImage.fromBitmap(croppedBitmap, 0)).addOnSuccessListener {
                image.close()
            }
        } /*else {
            //imageCropPercentages.postValue(CameraHolderState.Failure)
            recognizeCode(InputImage.fromBitmap(convertImageToBitmap, 0)).addOnSuccessListener {
                image.close()
            }
        }*/
    }

    private fun rotateAndCrop(
        bitmap: Bitmap,
        imageRotationDegrees: Int,
        cropRect: Rect
    ): Bitmap {
        val matrix = Matrix()
        matrix.preRotate(imageRotationDegrees.toFloat())
        return Bitmap.createBitmap(
            bitmap,
            cropRect.left,
            cropRect.top,
            cropRect.width(),
            cropRect.height(),
            matrix,
            true
        )
    }

    private fun Image.convertYuv420888ImageToBitmap(): Bitmap {
        require(this.format == ImageFormat.YUV_420_888) {
            "Unsupported image format $(image.format)"
        }

        val planes = this.planes

        val yuvBytes = planes.map { plane ->
            val buffer = plane.buffer
            val yuvBytes = ByteArray(buffer.capacity())
            buffer[yuvBytes]
            buffer.rewind()
            yuvBytes
        }

        val yRowStride = planes[0].rowStride
        val uvRowStride = planes[1].rowStride
        val uvPixelStride = planes[1].pixelStride
        val width = this.width
        val height = this.height
        @ColorInt val argb8888 = IntArray(width * height)
        var i = 0
        for (y in 0 until height) {
            val pY = yRowStride * y
            val uvRowStart = uvRowStride * (y shr 1)
            for (x in 0 until width) {
                val uvOffset = (x shr 1) * uvPixelStride
                argb8888[i++] =
                    yuvToRgb(
                        yuvBytes[0][pY + x].toIntUnsigned(),
                        yuvBytes[1][uvRowStart + uvOffset].toIntUnsigned(),
                        yuvBytes[2][uvRowStart + uvOffset].toIntUnsigned()
                    )
            }
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(argb8888, 0, width, 0, 0, width, height)
        return bitmap
    }

    @ColorInt
    private fun yuvToRgb(nY: Int, nU: Int, nV: Int): Int {
        var nY = nY
        var nU = nU
        var nV = nV
        nY -= 16
        nU -= 128
        nV -= 128
        nY = nY.coerceAtLeast(0)

        var nR = 1192 * nY + 1634 * nV
        var nG = 1192 * nY - 833 * nV - 400 * nU
        var nB = 1192 * nY + 2066 * nU

        nR = nR.coerceIn(CHANNEL_RANGE) shr 10 and 0xff
        nG = nG.coerceIn(CHANNEL_RANGE) shr 10 and 0xff
        nB = nB.coerceIn(CHANNEL_RANGE) shr 10 and 0xff
        return -0x1000000 or (nR shl 16) or (nG shl 8) or nB
    }

    private fun Byte.toIntUnsigned(): Int {
        return toInt() and 0xFF
    }

    companion object {
        var codeId: Long = 0
    }
}