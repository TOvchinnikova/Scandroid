package com.t_ovchinnikova.android.scandroid_2.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.t_ovchinnikova.android.scandroid_2.R
import com.t_ovchinnikova.android.scandroid_2.domain.Code
import com.t_ovchinnikova.android.scandroid_2.domain.formatToStringId
import java.util.*

@Preview
@Composable
fun HistoryScreen() {
    val list = listOf<Code>(
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

    LazyColumn(
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 72.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp))
    {
        items(
            items = list,
            key = { it.id }
        ) { code ->
            HistoryItem(code = code)
            Divider(color = Color.Black)
        }
    }
}

@Composable
fun HistoryItem(code: Code) {

    val drawableResource = if (code.isFavorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
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
        Column {
            Row {
                Text(
                    modifier = Modifier.weight(1F),
                    text = code.text
                )
                Image(
                    painter = painterResource(id = drawableResource),
                    contentDescription = null
                )
            }
            if (code.note.isNotBlank()) {
                Text(
                    color = colorResource(id = R.color.secondary_text_color),
                    text = code.note,
                    fontSize = 12.sp
                )
            }
            Row {
                Text(
                    modifier = Modifier.weight(1F),
                    color = colorResource(id = R.color.secondary_text_color),
                    text = code.date.toString(),
                    fontSize = 12.sp
                )
                Text(
                    text = stringResource(id = code.formatToStringId()),
                    color = colorResource(id = R.color.secondary_text_color),
                    fontSize = 12.sp
                )
            }
        }
    }
}