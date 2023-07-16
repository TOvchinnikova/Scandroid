package com.t_ovchinnikova.android.scandroid_2.code_list_impl.di

import com.t_ovchinnikova.android.scandroid_2.code_list_impl.HistoryViewModel
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.datasource.CodeListDataSource
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.datasource.impl.CodeListDataSourceImpl
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.usecases.interactors.DeleteAllCodesInteractor
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.usecases.interactors.GetCodesInteractor
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.repository.CodeListRepository
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.repository.impl.CodeListRepositoryImpl
import com.t_ovchinnikova.android.scandroid_2.core_db_impl.CodeDao
import com.t_ovchinnikova.android.scandroid_2.core_db_impl.mappers.CodeMapper
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.code_list_api.repository.usecases.DeleteAllCodesUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.DeleteCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.usecases.GetCodesUseCase
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.usecases.GetCodesWithFilterUseCase
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.usecases.interactors.GetCodesWithFilterInteractor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val codeListModule = module {

    single<CodeListDataSource> {
        CodeListDataSourceImpl(
            codeDao = get() as CodeDao
        )
    }

    single<CodeListRepository> {
        CodeListRepositoryImpl(
            codeMapper = get() as CodeMapper,
            codeDataSource = get() as CodeListDataSource
        )
    }

    factory<GetCodesUseCase> {
        GetCodesInteractor(
            repository = get() as CodeListRepository
        )
    }

    factory<DeleteAllCodesUseCase> {
        DeleteAllCodesInteractor(
            repository = get() as CodeListRepository
        )
    }

    factory<GetCodesWithFilterUseCase> {
        GetCodesWithFilterInteractor(
            repository = get() as CodeListRepository
        )
    }

    viewModel<HistoryViewModel> {
        HistoryViewModel(
            deleteAllCodesUseCase = get() as DeleteAllCodesUseCase,
            deleteCodeUseCase = get() as DeleteCodeUseCase,
            getCodesUseCase = get() as GetCodesUseCase,
            addCodeUseCase = get() as AddCodeUseCase,
            settingsUseCase = get() as com.t_ovchinnikova.android.scandroid_2.settings_api.usecases.GetSettingsUseCase
        )
    }
}