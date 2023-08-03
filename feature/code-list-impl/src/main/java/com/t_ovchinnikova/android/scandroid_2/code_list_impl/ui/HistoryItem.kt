package com.t_ovchinnikova.android.scandroid_2.code_list_impl.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.R
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeFormat
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeType
import com.t_ovchinnikova.android.scandroid_2.core_ui.SecondaryText
import com.t_ovchinnikova.android.scandroid_2.core_ui.theme.ColorPrimary
import com.t_ovchinnikova.android.scandroid_2.core_utils.toStringRes
import java.util.UUID

@Composable
fun HistoryItem(
    code: Code,
    onFavouriteClickListener: (code: Code) -> Unit,
    codeItemClickListener: (codeId: UUID) -> Unit,
    isSaveBarcodesToHistory: Boolean
) {
    Card(
        modifier = Modifier
            .clickable { codeItemClickListener(code.id) }
    ) {
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .clickable {
                            if (isSaveBarcodesToHistory) {
                                onFavouriteClickListener(code)
                            }
                        },
                    painter = painterResource(
                        id = if (code.isFavorite) {
                            R.drawable.ic_favorite_on
                        } else {
                            R.drawable.ic_favorite_off
                        }
                    ),
                    contentDescription = null
                )
                SecondaryText(
                    text = stringResource(id = code.format.toStringRes())
                )
            }
        }
    }
}

@Preview
@Composable
fun HistoryItemPreview() {
    HistoryItem(
        code = Code(
            id = UUID.randomUUID(),
            text = "1234567891011111",
            format = CodeFormat.QR_CODE,
            type = CodeType.TEXT,
            note = "Note",
            isFavorite = true
        ),
        onFavouriteClickListener = { },
        codeItemClickListener = { },
        isSaveBarcodesToHistory = false
    )
}