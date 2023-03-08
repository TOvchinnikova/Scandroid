package com.t_ovchinnikova.android.scandroid_2.data

import com.t_ovchinnikova.android.scandroid_2.core_db_impl.entity.CodeDbModel
import com.t_ovchinnikova.android.scandroid_2.domain.Code

class CodeMapper {

    fun mapEntityToDbModel(code: Code) =
        com.t_ovchinnikova.android.scandroid_2.core_db_impl.entity.CodeDbModel(
            id = code.id,
            text = code.text,
            format = code.format,
            type = code.type,
            date = code.date,
            note = code.note,
            isFavorite = code.isFavorite
        )

    fun mapDbModelToEntity(codeDbModel: com.t_ovchinnikova.android.scandroid_2.core_db_impl.entity.CodeDbModel) = Code(
        id = codeDbModel.id,
        text = codeDbModel.text,
        format = codeDbModel.format,
        type = codeDbModel.type,
        date = codeDbModel.date,
        note = codeDbModel.note,
        isFavorite = codeDbModel.isFavorite
    )

    fun mapListDbModelToListEntity(list: List<com.t_ovchinnikova.android.scandroid_2.core_db_impl.entity.CodeDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}