package com.t_ovchinnikova.android.scandroid_2.data.repository.impl

import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code
import com.t_ovchinnikova.android.scandroid_2.core_domain.repository.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.settings_impl.repository.SettingsRepository
import com.t_ovchinnikova.android.scandroid_2.data.CodeMapper
import com.t_ovchinnikova.android.scandroid_2.data.datasource.CodeDataSource
import com.t_ovchinnikova.android.scandroid_2.data.datasource.InMemoryCodeDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.*

class CodeRepositoryImpl(
    private val codeMapper: CodeMapper,
    private val codeDataSource: CodeDataSource,
    private val inMemoryCodeDataStore: InMemoryCodeDataStore,
    private val settingsRepository: SettingsRepository
) : CodeRepository {

    override suspend fun addCode(code: Code): Boolean {
        inMemoryCodeDataStore.setCode(code)
        val isNeedSaveToDb = settingsRepository.getSettings()?.isSaveScannedBarcodesToHistory ?: false
        return if (isNeedSaveToDb) {
            codeDataSource.addCode(code = codeMapper.mapEntityToDbModel(code)) != -1L
        } else {
            true
        }
    }

    override suspend fun deleteCode(codeId: UUID) {
        codeDataSource.deleteCode(codeId)
    }

    override suspend fun deleteAllCodes() {
        codeDataSource.deleteAllCodes()
    }

    override fun getCodes(): Flow<List<Code>> =
        codeDataSource.getCodes().map {
            codeMapper.mapListDbModelToListEntity(it)
        }

    override fun getCodesWithFilter(filterText: String): Flow<List<Code>> =
        codeDataSource.getCodesWithFilter(filterText).map {
            codeMapper.mapListDbModelToListEntity(it)
        }

    override fun getCodeByIdAsync(codeUuid: UUID): Flow<Code?> {
        return flow {
            if (inMemoryCodeDataStore.getCodeById(codeUuid) != null) {
                emitAll(inMemoryCodeDataStore.getCodeAsync())
                return@flow
            }
            emitAll(codeDataSource.getCodeByIdAsync(codeUuid).map {
                codeMapper.mapDbModelToEntity(it)
            })
        }
    }

    override fun getCodeById(id: UUID): Code? {
        return getCodeById(id)
    }
}