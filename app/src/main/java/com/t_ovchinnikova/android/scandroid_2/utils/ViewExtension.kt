package com.t_ovchinnikova.android.scandroid_2.utils

import android.content.res.Resources
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import com.t_ovchinnikova.android.scandroid_2.R

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

fun AppCompatButton.setSaveButtonState(isActive: Boolean, resources: Resources) {
    with(this) {
        if (isActive) {
            enable()
            background = resources
                .getDrawable(R.drawable.background_button_enabled, null)
            setTextColor(
                resources.getColor(android.R.color.white, null)
            )
        } else {
            disable()
            background = resources
                .getDrawable(R.drawable.background_button_disabled, null)
            setTextColor(
                resources.getColor(R.color.color_disable_text, null)
            )
        }
    }
}