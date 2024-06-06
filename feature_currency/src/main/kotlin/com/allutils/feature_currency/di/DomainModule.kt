package com.allutils.feature_currency.di

import com.allutils.feature_currency.domain.usecase.GetConversionRates
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val domainModule = module {

    singleOf(::GetConversionRates)

}
