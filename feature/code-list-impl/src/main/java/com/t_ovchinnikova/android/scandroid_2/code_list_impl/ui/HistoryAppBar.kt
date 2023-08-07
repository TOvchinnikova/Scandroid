package com.t_ovchinnikova.android.scandroid_2.code_list_impl.ui

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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.R
import com.t_ovchinnikova.android.scandroid_2.core_ui.EMPTY
import com.t_ovchinnikova.android.scandroid_2.core_ui.theme.ColorPrimary

@OptIn(ExperimentalMotionApi::class, ExperimentalMaterialApi::class)
@Composable
fun HistoryAppBar(
    title: String,
    deleteClickListener: () -> Unit,
    onSearchEditingListener: (String) -> Unit,
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
                    .background(MaterialTheme.colors.background)
            )

            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.layoutId("title")
            )
            Image(
                modifier = Modifier
                    .layoutId("delete_button")
                    .clickable { deleteClickListener.invoke() },
                imageVector = Icons.Filled.Delete,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White)
            )
            BasicTextField(
                value = searchState.value,
                singleLine = true,
                onValueChange = {
                    searchState.value = it
                    onSearchEditingListener.invoke(it)
                },
                interactionSource = interactionSource,
                textStyle = TextStyle(fontSize = 16.sp),
                modifier = Modifier
                    .layoutId("search_field")
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(start = 10.dp)
            ) { innerTextField ->
                TextFieldDefaults.TextFieldDecorationBox(
                    value = searchState.value,
                    visualTransformation = VisualTransformation.None,
                    innerTextField = innerTextField,
                    singleLine = true,
                    enabled = true,
                    interactionSource = interactionSource,
                    placeholder  = { Text(
                        text = stringResource(id = R.string.barcode_search_on_list),
                        modifier = Modifier.padding(0.dp)) },
                    contentPadding = PaddingValues(0.dp),
                    trailingIcon = {
                        if (searchState.value != EMPTY) IconButton(
                            onClick = {
                                searchState.value = EMPTY
                                onSearchEditingListener.invoke(EMPTY)
                            },
                            modifier = Modifier.size(20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Clear"
                            )
                        }
                    }
                )
            }
        }
    }
}