package com.t_ovchinnikova.android.scandroid_2.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.formatToStringId
import com.t_ovchinnikova.android.scandroid_2.ui.theme.ColorPrimary
import com.t_ovchinnikova.android.scandroid_2.ui.theme.ColorSecondary
import com.t_ovchinnikova.android.scandroid_2.ui.theme.ScandroidTheme
import java.util.*

@Preview
@Composable
private fun Preview() {
    ScandroidTheme {
        HistoryScreen()
    }
}

// todo здесь необходимо реализовать поиск по списку
// todo здесь необходимо реализовать удаление по свайпу влево
@Composable
fun HistoryScreen() {
    val list = listOf(
        Code(
            id = 1,
            text = "12klldgjldkgl;",
            format = 1,
            type = 1,

            date = Date(),
            note = "",
            isFavorite = false
        ),
        Code(
            id = 2,
            text = "rjtkhrtjoi546;",
            format = 32,
            type = 5,
            date = Date(),
            note = "Заметка",
            isFavorite = true
        ),
        Code(
            id = 3,
            text = "94856tlthgblkfjthjlktrj;",
            format = 32,
            type = 1,
            date = Date(),
            note = "Лучшая",
            isFavorite = false
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title =
                {
                    Text(text = stringResource(id = R.string.history))
                },
                actions = {
                    IconButton(onClick = { /* todo добавить слушатель */ }) {
                        Image(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues),
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 72.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            items(
                items = list,
                key = { it.id }
            ) { code ->
                HistoryItem(code = code)
            }
        }
    }
}

@Composable
fun HistoryItem(code: Code) {

    val drawableResource =
        if (code.isFavorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off

    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .background(
                        color = ColorPrimary,
                        shape = CircleShape
                    )
                    .padding(7.dp)
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_barcode),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = code.text
                )
                if (code.note.isNotBlank()) {
                    SecondaryText(
                        modifier = Modifier.padding(bottom = 4.dp),
                        text = code.note
                    )
                }
                SecondaryText(
                    text = code.date.toString()
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = drawableResource),
                    contentDescription = null
                )
                SecondaryText(
                    text = stringResource(id = code.formatToStringId())
                )
            }
        }
    }
}

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