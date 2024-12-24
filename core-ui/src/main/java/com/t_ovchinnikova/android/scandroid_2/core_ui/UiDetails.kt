package com.t_ovchinnikova.android.scandroid_2.core_ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.t_ovchinnikova.android.scandroid_2.core_ui.theme.ColorSecondary
import com.t_ovchinnikova.android.scandroid_2.core_ui.theme.ScandroidTheme

@Composable
fun CenterProgress(
    message: String? = null
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            if (message != null) {
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = message,
                )
            }
        }
    }
}

@Composable
fun CenterMessage(
    message: String,
    textColor: Color = ColorSecondary,
    @DrawableRes imageRes: Int? = null
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            imageRes?.let {
                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 4.dp),
                    painter = painterResource(id = it),
                    contentDescription = null
                )
            }
            Text(
                text = message,
                color = textColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CenterMessagePreview() {
    ScandroidTheme {
        CenterMessage(
            message = "The list is empty",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CenterProgressPreview() {
    ScandroidTheme {
        CenterProgress("Loading...")
    }
}