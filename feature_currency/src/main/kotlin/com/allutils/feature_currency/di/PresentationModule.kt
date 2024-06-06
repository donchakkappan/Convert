package com.allutils.feature_currency.di

import coil.ImageLoader
import com.allutils.feature_currency.presentation.CurrencyListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val presentationModule = module {

    single { ImageLoader(get()) }

    viewModelOf(::CurrencyListViewModel)
}
