package com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.CodeItemUiModel
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.CodeUiModel
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.mvi.HistoryUiState
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.ui.HistoryContent
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeFormat
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeType
import com.t_ovchinnikova.android.scandroid_2.core_ui.theme.ScandroidTheme
import java.util.UUID

@Preview
@Composable
fun HistoryContentPreviewLight() {
    HistoryContentPreview(false)
}

@Preview
@Composable
fun HistoryContentPreviewDark() {
    HistoryContentPreview(true)
}

@Composable
fun HistoryContentPreview(isDark: Boolean) {
    ScandroidTheme(isDark) {
        HistoryContent(
            state = HistoryUiState(
                isLoading = false,
                codes = listOf<CodeItemUiModel>(
                    CodeItemUiModel(
                        code = CodeUiModel(
                            id = UUID.randomUUID().toString(),
                            text = "12345678",
                            format = CodeFormat.DATA_MATRIX,
                            note = "Очень важный штрих-код",
                            dateTime = "23.12.2024 13:31",
                            isFavorite = true,
                            type = CodeType.TEXT
                        )
                    ),
                    CodeItemUiModel(
                        code = CodeUiModel(
                            id = UUID.randomUUID().toString(),
                            text = "1234567891234",
                            format = CodeFormat.EAN_13,
                            dateTime = "23.12.2024 13:31",
                            isFavorite = false,
                            type = CodeType.TEXT
                        )
                    ),
                    CodeItemUiModel(
                        code = CodeUiModel(
                            id = UUID.randomUUID().toString(),
                            text = "89585691785",
                            format = CodeFormat.QR_CODE,
                            dateTime = "23.12.2024 13:31",
                            isFavorite = false,
                            type = CodeType.PHONE
                        )
                    ),
                )
            ),
            onAction = {},
            codeItemClickListener = {}
        )
    }
}