package com.allutils.feature_emi.di

import com.allutils.feature_emi.presentation.home.EMICalculatorViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val presentationModule = module {
    viewModelOf(::EMICalculatorViewModel)
}
