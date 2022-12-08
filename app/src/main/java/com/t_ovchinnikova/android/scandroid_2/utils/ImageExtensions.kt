package com.t_ovchinnikova.android.scandroid_2.utils

import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.media.Image
import androidx.annotation.ColorInt

fun Image.convertYuv420888ImageToBitmap(): Bitmap {
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
    @ColorInt val argb8888 = IntArray(this.width * this.height)
    var i = 0
    for (y in 0 until this.height) {
        val pY = yRowStride * y
        val uvRowStart = uvRowStride * (y shr 1)
        for (x in 0 until this.width) {
            val uvOffset = (x shr 1) * uvPixelStride
            argb8888[i++] =
                yuvToRgb(
                    yuvBytes[0][pY + x].toIntUnsigned(),
                    yuvBytes[1][uvRowStart + uvOffset].toIntUnsigned(),
                    yuvBytes[2][uvRowStart + uvOffset].toIntUnsigned()
                )
        }
    }
    val bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
    bitmap.setPixels(argb8888, 0, this.width, 0, 0, this.width, this.height)
    return bitmap
}

@ColorInt
private fun yuvToRgb(nY: Int, nU: Int, nV: Int): Int {
    val channelRange = 0 until (1 shl 18)
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

    nR = nR.coerceIn(channelRange) shr 10 and 0xff
    nG = nG.coerceIn(channelRange) shr 10 and 0xff
    nB = nB.coerceIn(channelRange) shr 10 and 0xff
    return -0x1000000 or (nR shl 16) or (nG shl 8) or nB
}

private fun Byte.toIntUnsigned(): Int {
    return toInt() and 0xFF
}