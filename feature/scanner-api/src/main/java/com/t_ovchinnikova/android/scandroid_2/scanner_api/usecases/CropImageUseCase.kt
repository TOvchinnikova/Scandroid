package com.t_ovchinnikova.android.scandroid_2.scanner_api.usecases

import android.graphics.Bitmap
import android.media.Image
import androidx.camera.core.ImageProxy

interface CropImageUseCase {

    operator fun invoke(
        image: ImageProxy,
        mediaImage: Image,
        heightCropPercent: Int,
        widthCropPercent: Int
    ): Bitmap?
}