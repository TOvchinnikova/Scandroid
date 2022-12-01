package com.t_ovchinnikova.android.scandroid_2.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.t_ovchinnikova.android.scandroid_2.R

@Composable
fun HistoryScreen() {

}

@Preview
@Composable
fun HistoryItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Image(
            modifier = Modifier
                .background(
                    color = colorResource(id = R.color.colorPrimary),
                    shape = CircleShape
                )
                .padding(7.dp)
                .size(24.dp),
            painter = painterResource(id = R.drawable.ic_barcode),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column() {
            Row {
                Text(text = "Code text")
                Image(
                    painter = painterResource(id = R.drawable.ic_favorite_on),
                    contentDescription = null
                )
            }
            Row {
                Text(text = "Date")
                Text(text = "Note")
                Text(text = "Format")
            }
        }
    }
}