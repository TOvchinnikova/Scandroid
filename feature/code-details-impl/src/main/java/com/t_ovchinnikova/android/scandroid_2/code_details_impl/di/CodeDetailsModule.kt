package com.t_ovchinnikova.android.scandroid_2.code_details_impl.di

import com.t_ovchinnikova.android.scandroid_2.code_details_api.CodeDetailsFeature
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.navigation.CodeDetailsFeatureImpl
import com.t_ovchinnikova.android.scandroid_2.code_details_impl.viewmodel.CodeDetailsViewModel
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.AddCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.DeleteCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.GetCodeUseCase
import com.t_ovchinnikova.android.scandroid_2.core_domain.usecases.GetSettingsUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.UUID

val codeDetailsModule = module {

    factory<CodeDetailsFeature> {
        CodeDetailsFeatureImpl()
    }

    viewModel<CodeDetailsViewModel> { (codeId: UUID) ->
        CodeDetailsViewModel(
            codeId = codeId,
            deleteCodeUseCase = get() as DeleteCodeUseCase,
            addCodeUseCase = get() as AddCodeUseCase,
            getCodeUseCase = get() as GetCodeUseCase,
            getSettingsUseCase = get() as GetSettingsUseCase
        )
    }
}