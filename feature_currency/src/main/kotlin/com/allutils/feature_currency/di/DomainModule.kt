package com.allutils.feature_currency.di

import com.allutils.feature_currency.domain.usecase.AnyFavoriteConversion
import com.allutils.feature_currency.domain.usecase.GetAllConversionRates
import com.allutils.feature_currency.domain.usecase.GetFavoriteConversionRates
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val domainModule = module {

    singleOf(::GetAllConversionRates)

    singleOf(::GetFavoriteConversionRates)

    singleOf(::AnyFavoriteConversion)
}
