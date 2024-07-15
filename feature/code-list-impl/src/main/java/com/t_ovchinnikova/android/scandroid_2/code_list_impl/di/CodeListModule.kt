package com.t_ovchinnikova.android.scandroid_2.code_list_impl.di

import com.t_ovchinnikova.android.scandroid_2.code_list_impl.data.datasource.CodeListDataSource
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.data.datasource.impl.CodeListDataSourceImpl
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.data.repository.CodeListRepositoryImpl
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.domain.repository.CodeListRepository
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.domain.usecase.DeleteAllCodesUseCase
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.domain.usecase.GetCodesUseCase
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.domain.usecase.GetCodesWithFilterUseCase
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.domain.usecase.interactor.DeleteAllCodesInteractor
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.domain.usecase.interactor.GetCodesInteractor
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.domain.usecase.interactor.GetCodesWithFilterInteractor
import com.t_ovchinnikova.android.scandroid_2.code_list_impl.presentation.viewmodel.HistoryViewModel
import com.t_ovchinnikova.android.scandroid_2.core_db_impl.CodeDao
import com.t_ovchinnikova.android.scandroid_2.core_db_impl.mappers.CodeMapper
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.DeleteCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_executor.CoroutineDispatcherProvider
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
            dispatcher = (get() as CoroutineDispatcherProvider).io
        )
    }
}