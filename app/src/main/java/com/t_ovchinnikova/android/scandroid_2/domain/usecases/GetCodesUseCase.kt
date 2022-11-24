package com.t_ovchinnikova.android.scandroid_2.domain.usecases

import androidx.lifecycle.LiveData
import com.t_ovchinnikova.android.scandroid_2.domain.Code

interface GetCodesUseCase {

    operator fun invoke(): LiveData<List<Code>>
}