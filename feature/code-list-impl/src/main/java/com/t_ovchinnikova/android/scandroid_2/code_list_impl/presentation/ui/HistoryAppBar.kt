package com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.R
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.model.mvi.HistoryUiAction
import com.t_ovchinnikova.android.scandroid_2.core_ui.EMPTY
import com.t_ovchinnikova.android.scandroid_2.core_ui.theme.SearchFieldBackgroundColor
import com.t_ovchinnikova.android.scandroid_2.core_resources.R as CoreResources

@OptIn(ExperimentalMotionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HistoryAppBar(
    title: String,
    onAction: (HistoryUiAction) -> Unit,
    progress: Float,
    motionHeight: Dp
) {

    val context = LocalContext.current
    val motionScene = remember {
        context.resources.openRawResource(R.raw.motion_scene).readBytes().decodeToString()
    }
    val interactionSource = remember { MutableInteractionSource() }

    val searchState = rememberSaveable {
        mutableStateOf(EMPTY)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        MotionLayout(
            motionScene = MotionScene(content = motionScene),
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(motionHeight)
        ) {

            Box(
                modifier = Modifier
                    .layoutId("app_bar")
                    .background(MaterialTheme.colorScheme.background)
            )

            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.layoutId("title")
            )
            Image(
                modifier = Modifier
                    .layoutId("delete_button")
                    .clickable { onAction(HistoryUiAction.ShowDeleteDialog) },
                imageVector = Icons.Filled.Delete,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
            BasicTextField(
                value = searchState.value,
                singleLine = true,
                onValueChange = {
                    searchState.value = it
                    onAction(HistoryUiAction.UpdateSearchCondition(it))
                },
                interactionSource = interactionSource,
                textStyle = TextStyle(fontSize = 16.sp),
                modifier = Modifier
                    .layoutId("app_bar_content_field")
                    .background(
                        color = SearchFieldBackgroundColor,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(start = 10.dp, end = 10.dp)

            ) { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    value = searchState.value,
                    visualTransformation = VisualTransformation.None,
                    innerTextField = innerTextField,
                    singleLine = true,
                    enabled = true,
                    interactionSource = interactionSource,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = SearchFieldBackgroundColor,
                        unfocusedContainerColor = SearchFieldBackgroundColor,
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.secondary,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.secondary,
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(id = CoreResources.string.barcode_search_on_list),
                            modifier = Modifier.padding(0.dp)
                        )
                    },
                    contentPadding = PaddingValues(0.dp),
                    trailingIcon = {
                        if (searchState.value != EMPTY) IconButton(
                            onClick = {
                                searchState.value = EMPTY
                                onAction(HistoryUiAction.UpdateSearchCondition(EMPTY))
                            },
                            modifier = Modifier.size(20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Clear",
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun HistoryAppBarCollapsedPreview() {
    HistoryAppBar(
        title = "History",
        progress = 1f,
        onAction = {},
        motionHeight = 56.dp,
    )
}

@Preview
@Composable
fun HistoryAppBarPreview() {
    HistoryAppBar(
        title = "History",
        progress = 0f,
        onAction = {},
        motionHeight = 112.dp,
    )
}