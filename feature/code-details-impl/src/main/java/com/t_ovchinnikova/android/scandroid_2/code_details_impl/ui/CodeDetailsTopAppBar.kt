package com.t_ovchinnikova.android.scandroid_2.code_details_impl.ui

import androidx.compose.foundation.Image
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.R
import com.t_ovchinnikova.android.scandroid_2.core_ui.EMPTY_STRING

@Composable
fun CodeDetailsTopAppBar(
    title: String = EMPTY_STRING,
    onBackPressed: () -> Unit,
    isFavourite: Boolean = false,
    onFavouriteClickListener: (() -> Unit)? = null,
    onDeleteClickListener: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        actions = {
            onFavouriteClickListener?.let { clickListener ->
                IconButton(onClick = { clickListener() }) {
                    Image(
                        painter = painterResource(id = if (isFavourite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off),
                        contentDescription = null
                    )
                }
            }
            onDeleteClickListener?.let { clickListener ->
                IconButton(onClick = { clickListener() }) {
                    Image(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            }
        }
    )
}