package com.t_ovchinnikova.android.scandroid_2.core_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.t_ovchinnikova.android.scandroid_2.core_ui.theme.ColorPrimary

@Composable
fun ActionButton(
    titleResId: Int,
    iconResId: Int,
    onButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .clickable(enabled = true, onClick = onButtonClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(shape = CircleShape, color = ColorPrimary)
        ) {
            Icon(
                modifier = Modifier
                    .padding(10.dp)
                    .size(16.dp),
                painter = painterResource(id = iconResId),
                contentDescription = null,
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = stringResource(id = titleResId))
    }
}