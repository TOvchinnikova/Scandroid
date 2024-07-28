package com.t_ovchinnikova.android.scandroid_2.code_details_impl.presentation.model.mvi

import com.t_ovchinnikova.android.scandroid_2.core_mvi.UiAction

interface CodeDetailsUiAction : UiAction {

    object DeleteBarcode : CodeDetailsUiAction

    object FavouriteClicked : CodeDetailsUiAction

    data class NoteChanged(val note: String) : CodeDetailsUiAction

    object CopyCodeValueToClipboard : CodeDetailsUiAction

    object SearchOnWeb : CodeDetailsUiAction

    object ShareCodeValue : CodeDetailsUiAction

    object ShowDeleteDialog : CodeDetailsUiAction

    object HideDeleteDialog : CodeDetailsUiAction
}