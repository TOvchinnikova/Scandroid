package com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.ui

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.CodeItemUiModel
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.CodeUiModel
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.mvi.HistoryUiAction
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeFormat
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeType
import com.t_ovchinnikova.android.scandroid_2.core_ui.SecondaryText
import com.t_ovchinnikova.android.scandroid_2.core_ui.theme.ColorPrimary
import com.t_ovchinnikova.android.scandroid_2.core_ui.theme.ScandroidTheme
import com.t_ovchinnikova.android.scandroid_2.core_utils.toImageId
import com.t_ovchinnikova.android.scandroid_2.core_utils.toStringRes
import java.util.UUID
import com.t_ovchinnikova.android.scandroid_2.core_resources.R as CoreResources

@Composable
fun HistoryItem(
    codeModel: CodeItemUiModel,
    isVisibleCheckBox: Boolean,
    onAction: (HistoryUiAction) -> Unit,
    codeItemClickListener: (codeId: String) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.0.dp),
        modifier = Modifier
            .clickable { codeItemClickListener(codeModel.code.id) }
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
                painter = painterResource(id = codeModel.code.format.toImageId()),
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
                    color = MaterialTheme.colorScheme.onBackground,
                    text = codeModel.code.text
                )
                if (codeModel.code.note.isNotBlank()) {
                    SecondaryText(
                        modifier = Modifier.padding(bottom = 4.dp),
                        text = codeModel.code.note
                    )
                }
                SecondaryText(
                    text = codeModel.code.dateTime
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .clickable {
                            onAction(HistoryUiAction.ToggleFavourite(codeModel.code))
                        },
                    painter = painterResource(
                        id = if (codeModel.code.isFavorite) {
                            CoreResources.drawable.ic_favorite_on
                        } else {
                            CoreResources.drawable.ic_favorite_off
                        }
                    ),
                    contentDescription = null
                )
                SecondaryText(
                    text = stringResource(id = codeModel.code.format.toStringRes())
                )
            }
        }
    }
}

@Preview
@Composable
private fun HistoryItemPreview() {
    ScandroidTheme {
        HistoryItem(
            codeModel = CodeItemUiModel(
                code = CodeUiModel(
                    id = UUID.randomUUID().toString(),
                    text = "12345678910111115454545454454545454545454545454454545454",
                    format = CodeFormat.QR_CODE,
                    type = CodeType.TEXT,
                    note = "Note",
                    isFavorite = true
                )
            ),
            isVisibleCheckBox = true,
            onAction = { },
            codeItemClickListener = { }
        )
    }
}

@Preview
@Composable
private fun HistoryItemPreviewDark() {
    ScandroidTheme(true) {
        HistoryItem(
            codeModel = CodeItemUiModel(
                code = CodeUiModel(
                    id = UUID.randomUUID().toString(),
                    text = "12345678910111115454545454454545454545454545454454545454",
                    format = CodeFormat.QR_CODE,
                    type = CodeType.TEXT,
                    note = "Note",
                    isFavorite = true
                )
            ),
            isVisibleCheckBox = true,
            onAction = { },
            codeItemClickListener = { }
        )
    }
}