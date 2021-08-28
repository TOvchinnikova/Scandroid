package com.t_ovchinnikova.android.scandroid_2.presentation

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.t_ovchinnikova.android.scandroid_2.R

class ViewFinderOverlay(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val boxPaint: Paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.barcode_reticle_stroke)
        style = Paint.Style.STROKE
        strokeWidth = context.resources.getDimensionPixelOffset(R.dimen.barcode_stroke_width).toFloat()
    }

    private val scrimPaint: Paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.barcode_reticle_background)
    }

    private val eraserPaint: Paint = Paint().apply {
        strokeWidth = boxPaint.strokeWidth
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    private val boxCornerRadius: Float =
        context.resources.getDimensionPixelOffset(R.dimen.barcode_reticle_corner_radius).toFloat()

    var boxRect: RectF? = null

    fun setViewFinder() {

        val overlayWidth =  width.toFloat()
        val overlayHeight = height.toFloat()

        val rectTop = overlayHeight * DESIRED_HEIGHT_CROP_PERCENT / 2 / 100f
        val rectLeft = overlayWidth * DESIRED_WIDTH_CROP_PERCENT / 2 / 100f
        val rectRight = overlayWidth * (1 - DESIRED_WIDTH_CROP_PERCENT / 2 / 100f)
        val rectBottom = overlayHeight * (1 - DESIRED_HEIGHT_CROP_PERCENT / 2 / 100f)
        boxRect = RectF(rectLeft, rectTop, rectRight, rectBottom)

        invalidate()

    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        boxRect?.let {

            canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), scrimPaint)

            eraserPaint.style = Paint.Style.FILL
            canvas.drawRoundRect(it, boxCornerRadius, boxCornerRadius, eraserPaint)
            eraserPaint.style = Paint.Style.STROKE
            canvas.drawRoundRect(it, boxCornerRadius, boxCornerRadius, eraserPaint)
            canvas.drawRoundRect(it, boxCornerRadius, boxCornerRadius, boxPaint)
        }
    }

    companion object {
        const val DESIRED_WIDTH_CROP_PERCENT = 20
        const val DESIRED_HEIGHT_CROP_PERCENT = 74
    }
}