package com.t_ovchinnikova.android.scandroid_2.code_details_impl.domain.repository

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.CodeEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

interface CodeRepository {

    suspend fun addCode(code: CodeEntity): Boolean

    suspend fun deleteCode(codeId: UUID)

    fun getCodeByIdAsync(codeUuid: UUID): Flow<CodeEntity?>

    fun getCodeById(id: UUID): CodeEntity?

    fun updateFavoriteToggle(codeId: UUID, isFavorite: Boolean): Boolean
}