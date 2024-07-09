package com.allutils.feature_emi.di

import com.allutils.feature_emi.domain.usecase.CalculateEmi
import com.allutils.feature_emi.domain.usecase.CalculateInterest
import com.allutils.feature_emi.domain.usecase.CalculatePrinciple
import com.allutils.feature_emi.domain.usecase.CalculateTenure
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val domainModule = module {
    singleOf(::CalculateEmi)
    singleOf(::CalculateInterest)
    singleOf(::CalculateTenure)
    singleOf(::CalculatePrinciple)
}
