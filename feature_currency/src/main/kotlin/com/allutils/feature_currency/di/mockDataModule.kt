package com.allutils.feature_currency.di

import com.allutils.feature_currency.data.CurrenciesRepositoryMockImpl
import com.allutils.feature_currency.domain.CurrenciesRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val mockDataModule = module {

    single<CurrenciesRepository> { CurrenciesRepositoryMockImpl(androidContext()) }

}
