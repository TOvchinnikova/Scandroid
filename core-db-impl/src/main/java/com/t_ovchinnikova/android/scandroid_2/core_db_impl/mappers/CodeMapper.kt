package com.t_ovchinnikova.android.scandroid_2.core_db_impl.mappers

import com.t_ovchinnikova.android.scandroid_2.core_db_impl.entity.CodeDbModel
import com.t_ovchinnikova.android.scandroid_2.core_domain.entity.Code

class CodeMapper {

    fun mapEntityToDbModel(code: Code): CodeDbModel = CodeDbModel(
            id = code.id,
            text = code.text,
            format = code.format,
            type = code.type,
            date = code.date,
            note = code.note,
            isFavorite = code.isFavorite
        )

    fun mapDbModelToEntity(codeDbModel: CodeDbModel): Code = Code(
            id = codeDbModel.id,
            text = codeDbModel.text,
            format = codeDbModel.format,
            type = codeDbModel.type,
            date = codeDbModel.date,
            note = codeDbModel.note,
            isFavorite = codeDbModel.isFavorite
        )

    fun mapListDbModelToListEntity(list: List<CodeDbModel>): List<Code> = list.map {
        mapDbModelToEntity(it)
    }
}