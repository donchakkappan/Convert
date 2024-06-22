package com.allutils.feature_currency.di

import com.allutils.feature_currency.data.CurrenciesRepositoryMockImpl
import com.allutils.feature_currency.domain.ICurrenciesRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val mockDataModule = module {

    single<ICurrenciesRepository> { CurrenciesRepositoryMockImpl(androidContext()) }

}
