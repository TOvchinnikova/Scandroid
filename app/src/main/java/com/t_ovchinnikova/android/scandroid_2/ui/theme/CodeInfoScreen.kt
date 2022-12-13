package com.t_ovchinnikova.android.scandroid_2.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.ui.DividerPrimaryColor
import com.t_ovchinnikova.android.scandroid_2.ui.SecondaryText

@Preview
@Composable
fun CodeInfoScreen() {
    ScandroidTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "EAN-13")
                    },
                    navigationIcon = {
                        Image(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_favorite_on),
                                contentDescription = null
                            )
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Image(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        }
                    }
                )
            },
        ) {
            Content()
        }
    }
}

@Composable
fun Content() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SecondaryText(text = "13.12.2022 22:52")
            Image(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Заметка")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "889958375835934")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Текст")
        DividerPrimaryColor()
    }
}