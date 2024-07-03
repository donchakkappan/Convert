package com.allutils.feature_emi.di

import com.allutils.feature_emi.domain.usecase.GetEmiDetails
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val domainModule = module {
    singleOf(::GetEmiDetails)
}
