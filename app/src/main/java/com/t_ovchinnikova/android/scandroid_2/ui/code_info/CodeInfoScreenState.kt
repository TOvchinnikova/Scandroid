package com.t_ovchinnikova.android.scandroid_2.ui.code_info

import com.t_ovchinnikova.android.scandroid_2.domain.Code

sealed class CodeInfoScreenState {

    object Initial : CodeInfoScreenState()

    object Loading : CodeInfoScreenState()

    data class CodeInfo(
        val code: Code,
        val isFromDatabase: Boolean,
        val isSendingNoteWithCode: Boolean
    ) : CodeInfoScreenState()

    object CodeNotFound : CodeInfoScreenState()
}
