package com.t_ovchinnikova.android.scandroid_2.scanner_impl.interactors

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Rect
import android.media.Image
import androidx.camera.core.ImageProxy
import com.t_ovchinnikova.android.scandroid_2.scanner_api.usecases.CropImageUseCase
import com.t_ovchinnikova.android.scandroid_2.scanner_impl.convertYuv420888ImageToBitmap

class CropImageInteractor : CropImageUseCase {

    override fun invoke(
        image: ImageProxy,
        mediaImage: Image,
        heightCropPercent: Int,
        widthCropPercent: Int
    ): Bitmap? {
        val rotationDegrees = image.imageInfo.rotationDegrees
        val convertImageToBitmap = mediaImage.convertYuv420888ImageToBitmap()
        val cropRect = Rect(0, 0, mediaImage.width, mediaImage.height)
        val (widthCrop, heightCrop) = when (rotationDegrees) {
            90, 270 -> Pair(heightCropPercent / 100f, widthCropPercent / 100f)
            else -> Pair(widthCropPercent / 100f, heightCropPercent / 100f)
        }
        cropRect.inset(
            (mediaImage.width * widthCrop / 2).toInt(),
            (mediaImage.height * heightCrop / 2).toInt()
        )
        return if (cropRect.height() > 0 && cropRect.width() > 0) {
            rotateAndCrop(convertImageToBitmap, rotationDegrees, cropRect)
        } else {
            null
        }
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
}