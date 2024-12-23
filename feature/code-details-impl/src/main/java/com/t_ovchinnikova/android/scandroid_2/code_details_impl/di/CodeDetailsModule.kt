package com.t_ovchinnikova.android.scandroid_2.code_details_impl.di

import com.t_ovchinnikova.android.scandroid_2.code_details_impl.data.datasource.impl.CodeDataSourceImpl
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.domain.interactor.AddCodeInteractor
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.domain.interactor.DeleteCodeInteractor
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.domain.interactor.GetCodeInteractor
import com.t_ovchinnikova.android.scandroid_2.core_db_impl.mappers.CodeMapper
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.data.repository.CodeRepositoryImpl
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.presentation.viewmodel.CodeDetailsViewModel
import com.t_ovchinnikova.android.scandroid_2.core_db_impl.CodeDao
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.data.datasource.CodeDataSource
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.domain.interactor.UpdateCodeInteractor
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.domain.repository.CodeRepository
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.DeleteCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.GetCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.UpdateCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_executor.CoroutineDispatcherProvider
import com.t_ovchinnikova.android.scandroid_2.settings_api.usecases.GetSettingsUseCase
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.UUID

val codeDetailsModule = module {

    single<CodeDataSource> {
        CodeDataSourceImpl(
            codeMapper = get() as CodeMapper,
            codeDao = get() as CodeDao
        )
    }

    single<CodeRepository> {
        CodeRepositoryImpl(
            codeDataSource = get() as CodeDataSource
        )
    }

    single<CodeMapper> {
        CodeMapper()
    }

    factory<GetCodeUseCase> {
        GetCodeInteractor(
            repository = get() as CodeRepository
        )
    }

    factory<AddCodeUseCase> {
        AddCodeInteractor(
            repository = get() as CodeRepository,
            dispatcher = (get() as CoroutineDispatcherProvider).io
        )
    }

    factory<DeleteCodeUseCase> {
        DeleteCodeInteractor(
            repository = get() as CodeRepository,
            dispatcher = (get() as CoroutineDispatcherProvider).io
        )
    }

    factory<UpdateCodeUseCase> {
        UpdateCodeInteractor(
            repository = get() as CodeRepository,
            dispatcher = (get() as CoroutineDispatcherProvider).io
        )
    }

    viewModel<CodeDetailsViewModel> { (codeId: UUID) ->
        CodeDetailsViewModel(
            codeId = codeId,
            context = androidApplication(),
            deleteCodeUseCase = get() as DeleteCodeUseCase,
            addCodeUseCase = get() as AddCodeUseCase,
            dispatcher = (get() as CoroutineDispatcherProvider).io,
            getCodeUseCase = get() as GetCodeUseCase,
            getSettingsUseCase = get() as GetSettingsUseCase
        )
    }
}