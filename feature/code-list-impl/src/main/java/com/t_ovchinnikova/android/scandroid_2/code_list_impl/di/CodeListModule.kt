package com.t_ovchinnikova.android.scandroid_2.code_list_impl.di

import com.t_ovchinnikova.android.scandroid_2.code_list_impl.HistoryViewModel
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.DeleteAllCodesUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.DeleteCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.GetCodesUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.GetSettingsUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val codeListModule = module {

    viewModel<HistoryViewModel> {
        HistoryViewModel(
            deleteAllCodesUseCase = get() as DeleteAllCodesUseCase,
            deleteCodeUseCase = get() as DeleteCodeUseCase,
            getCodesUseCase = get() as GetCodesUseCase,
            addCodeUseCase = get() as AddCodeUseCase,
            settingsUseCase = get() as GetSettingsUseCase
        )
    }
}