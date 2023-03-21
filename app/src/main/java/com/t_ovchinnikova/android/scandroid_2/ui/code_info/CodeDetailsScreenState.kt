package com.t_ovchinnikova.android.scandroid_2.ui.code_info

import com.t_ovchinnikova.android.scandroid_2.domain.Code

sealed class CodeDetailsScreenState {

    object Initial : CodeDetailsScreenState()

    object Loading : CodeDetailsScreenState()

    data class CodeDetails(
        val code: Code,
        val isFromDatabase: Boolean,
        val isSendingNoteWithCode: Boolean
    ) : CodeDetailsScreenState()

    object CodeNotFound : CodeDetailsScreenState()
}
