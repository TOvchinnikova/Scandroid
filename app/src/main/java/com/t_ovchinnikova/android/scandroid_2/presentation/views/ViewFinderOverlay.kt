package com.t_ovchinnikova.android.scandroid_2.presentation.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.t_ovchinnikova.android.scandroid_2.R
import kotlin.properties.Delegates

class ViewFinderOverlay(
    context: Context,
    attrs: AttributeSet?
) : View(context, attrs) {

    private var strokeColor by Delegates.notNull<Int>()
    private var backgroundRecticle by Delegates.notNull<Int>()
    private var strokeBoxWidth by Delegates.notNull<Float>()
    private var boxCornerRadius by Delegates.notNull<Float>()

    private var desiredWidthCropPercent by Delegates.notNull<Int>()
    private var desiredHeightCropPercent by Delegates.notNull<Int>()

    private val boxPaint by lazy {
        Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = strokeBoxWidth
            color = strokeColor
        }
    }

    private val scrimPaint by lazy {
        Paint().apply {
            color = backgroundRecticle
        }
    }

    private val eraserPaint by lazy {
        Paint().apply {
            strokeWidth = boxPaint.strokeWidth
            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        }
    }

    private var boxRect: RectF? = null

    init {
        if (attrs != null) {
            initAttributes(attrs)
        } else {
            initDefaultAttrsValues()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        setViewFinder()
    }

    private fun initAttributes(attributesSet: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(
            attributesSet,
            R.styleable.ViewFinderOverlay
        )

        strokeColor =
            typedArray.getColor(R.styleable.ViewFinderOverlay_strokeColor, STROKE_DEFAULT_COLOR)
        backgroundRecticle = typedArray.getColor(
            R.styleable.ViewFinderOverlay_backgroundRecticle,
            BACKGROUND_RECTICLE_DEFAULT_COLOR
        )
        strokeBoxWidth = typedArray.getFloat(
            R.styleable.ViewFinderOverlay_strokeBoxWidth,
            context.resources.getDimensionPixelOffset(STROKE_BOX_WIDTH_DEFAULT).toFloat()
        )
        boxCornerRadius = typedArray.getFloat(
            R.styleable.ViewFinderOverlay_boxCornerRadius,
            context.resources.getDimensionPixelOffset(BOX_CORNER_RADIUS_DEFAULT).toFloat()
        )
        desiredHeightCropPercent = typedArray.getInt(
            R.styleable.ViewFinderOverlay_desiredHeightCropPercent,
            DESIRED_HEIGHT_CROP_PERCENT_DEFAULT
        )
        desiredWidthCropPercent = typedArray.getInt(
            R.styleable.ViewFinderOverlay_desiredWidthCropPercent,
            DESIRED_WIDTH_CROP_PERCENT_DEFAULT
        )

        typedArray.recycle()
    }

    private fun initDefaultAttrsValues() {
        strokeColor = STROKE_DEFAULT_COLOR
        backgroundRecticle = BACKGROUND_RECTICLE_DEFAULT_COLOR
        strokeBoxWidth = context.resources.getDimensionPixelOffset(STROKE_BOX_WIDTH_DEFAULT).toFloat()
        boxCornerRadius = context.resources.getDimensionPixelOffset(BOX_CORNER_RADIUS_DEFAULT).toFloat()
        desiredHeightCropPercent = DESIRED_HEIGHT_CROP_PERCENT_DEFAULT
        desiredWidthCropPercent = DESIRED_WIDTH_CROP_PERCENT_DEFAULT
    }

    private fun setViewFinder() {
        val overlayWidth = width.toFloat()
        val overlayHeight = height.toFloat()

        val rectTop = overlayHeight * desiredHeightCropPercent / 2 / 100f
        val rectLeft = overlayWidth * desiredWidthCropPercent / 2 / 100f
        val rectRight = overlayWidth * (1 - desiredWidthCropPercent / 2 / 100f)
        val rectBottom = overlayHeight * (1 - desiredHeightCropPercent / 2 / 100f)
        boxRect = RectF(rectLeft, rectTop, rectRight, rectBottom)

        invalidate()
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        boxRect?.let {

            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), scrimPaint)

            eraserPaint.style = Paint.Style.FILL
            canvas.drawRoundRect(it, boxCornerRadius, boxCornerRadius, eraserPaint)
            eraserPaint.style = Paint.Style.STROKE
            canvas.drawRoundRect(it, boxCornerRadius, boxCornerRadius, eraserPaint)
            canvas.drawRoundRect(it, boxCornerRadius, boxCornerRadius, boxPaint)
        }
    }

    companion object {
        const val DESIRED_WIDTH_CROP_PERCENT_DEFAULT = 20
        const val DESIRED_HEIGHT_CROP_PERCENT_DEFAULT = 74

        const val BACKGROUND_RECTICLE_DEFAULT_COLOR = R.color.barcode_recticle_background
        const val STROKE_DEFAULT_COLOR = Color.WHITE

        const val STROKE_BOX_WIDTH_DEFAULT = R.dimen.barcode_stroke_width
        const val BOX_CORNER_RADIUS_DEFAULT = R.dimen.barcode_recticle_corner_radius
    }
}