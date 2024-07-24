package com.t_ovchinnikova.android.scandroid_2.code_details_impl.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.presentation.model.mvi.CodeDetailsUiAction
import com.t_ovchinnikova.android.scandroid_2.core_resources.R as CoreResources

@Composable
fun CodeDetailsTopAppBar(
    title: String,
    onBackPressed: () -> Unit,
    isFavourite: Boolean = false,
    onAction: (CodeDetailsUiAction) -> Unit,
    isVisibleButtons: Boolean = false
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        actions = {
            if (isVisibleButtons) {
                IconButton(
                    onClick = { onAction(CodeDetailsUiAction.FavouriteClicked) }
                ) {
                    Image(
                        painter = painterResource(
                            id = if (isFavourite) {
                                CoreResources.drawable.ic_favorite_on
                            } else {
                                CoreResources.drawable.ic_favorite_off
                            }
                        ),
                        contentDescription = null
                    )
                }
                IconButton(
                    onClick = { onAction(CodeDetailsUiAction.DeleteBarcode) }
                ) {
                    Image(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                    )
                }
            }
        }
    )
}