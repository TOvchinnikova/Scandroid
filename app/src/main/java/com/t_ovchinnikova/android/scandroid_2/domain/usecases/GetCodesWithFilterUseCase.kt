package com.t_ovchinnikova.android.scandroid_2.domain.usecases

import androidx.lifecycle.LiveData
import com.t_ovchinnikova.android.scandroid_2.domain.Code

interface GetCodesWithFilterUseCase {

    operator fun invoke(filterText: String): LiveData<List<Code>>
}