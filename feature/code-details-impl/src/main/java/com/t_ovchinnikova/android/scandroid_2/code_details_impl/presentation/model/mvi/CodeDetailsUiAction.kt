package com.t_ovchinnikova.android.scandroid_2.code_details_impl.presentation.model.mvi

import android.content.Context
import com.t_ovchinnikova.android.scandroid_2.core_mvi.UiAction

interface CodeDetailsUiAction : UiAction {

    object DeleteBarcode : CodeDetailsUiAction

    object FavouriteClicked : CodeDetailsUiAction

    data class NoteChanged(val note: String) : CodeDetailsUiAction

    object CopyCodeValueToClipboard : CodeDetailsUiAction

    data class SearchOnWeb(val context: Context) : CodeDetailsUiAction

    data class ShareCodeValue(val context: Context) : CodeDetailsUiAction

    object ShowDeleteDialog : CodeDetailsUiAction

    object HideDeleteDialog : CodeDetailsUiAction

    object ShowCommentDialog: CodeDetailsUiAction

    object HideCommentDialog : CodeDetailsUiAction
}