package com.t_ovchinnikova.android.scandroid_2.code_details_impl.ui

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code

sealed class CodeDetailsScreenState {

    object Initial : CodeDetailsScreenState()

    object Loading : CodeDetailsScreenState()

    data class CodeDetails(
        val code: Code,
        val isSendingNoteWithCode: Boolean
    ) : CodeDetailsScreenState()

    object CodeNotFound : CodeDetailsScreenState()
}
