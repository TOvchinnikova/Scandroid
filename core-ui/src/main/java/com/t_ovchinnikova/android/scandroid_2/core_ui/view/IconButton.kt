package com.t_ovchinnikova.android.scandroid_2.core_ui.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.t_ovchinnikova.android.scandroid_2.core_ui.R

class IconButton : FrameLayout {

    private val view: View

    private val tvTitleButton: TextView by lazy { findViewById(R.id.tvTitleButton) }
    private val imageViewSchema: ImageView by lazy { findViewById(R.id.imageViewSchema) }
    private val layoutImage: FrameLayout by lazy { findViewById(R.id.layoutImage) }

    var text: String
        get() = tvTitleButton.text.toString()
        set(value) {
            tvTitleButton.text = value
        }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr) {

        view = LayoutInflater
            .from(context)
            .inflate(R.layout.layout_icon_button, this, true)

        context.obtainStyledAttributes(attrs, R.styleable.IconButton).apply {
            showIcon(this)
            showIconBackgroundColor(this)
            showText(this)
            recycle()
        }
    }

    private fun showIcon(attributes: TypedArray) {
        val iconResId = attributes.getResourceId(R.styleable.IconButton_icon, -1)
        val icon = AppCompatResources.getDrawable(context, iconResId)
        imageViewSchema.setImageDrawable(icon)
    }

    private fun showIconBackgroundColor(attributes: TypedArray) {
        val color = attributes.getColor(
            R.styleable.IconButton_iconBackground,
            ContextCompat.getColor(view.context, R.color.light_blue)
        )
        (layoutImage.background.mutate() as GradientDrawable).setColor(color)
    }

    private fun showText(attributes: TypedArray) {
        tvTitleButton.text = attributes.getString(R.styleable.IconButton_text).orEmpty()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        imageViewSchema.isEnabled = enabled
        tvTitleButton.isEnabled = enabled
    }
}