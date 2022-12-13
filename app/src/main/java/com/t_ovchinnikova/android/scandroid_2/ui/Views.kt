package com.t_ovchinnikova.android.scandroid_2.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.t_ovchinnikova.android.scandroid_2.ui.theme.ColorPrimary
import com.t_ovchinnikova.android.scandroid_2.ui.theme.ColorSecondary


@Composable
fun SecondaryText(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        color = ColorSecondary,
        text = text,
        fontSize = 12.sp
    )
}

@Composable
fun DividerPrimaryColor() {
    Divider(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .background(ColorPrimary)
            .height(1.dp)
    )
}