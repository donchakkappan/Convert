package com.allutils.feature_currency.di

import androidx.room.Room
import com.allutils.feature_currency.data.CurrenciesRepositoryImpl
import com.allutils.feature_currency.data.local.CurrenciesDatabase
import com.allutils.feature_currency.data.network.ConversionRatesNetworkService
import com.allutils.feature_currency.domain.CurrenciesRepository
import org.koin.dsl.module
import retrofit2.Retrofit

internal val dataModule = module {

    single<CurrenciesRepository> { CurrenciesRepositoryImpl(get(), get()) }

    single { get<Retrofit>().create(ConversionRatesNetworkService::class.java) }

    single {
        Room.databaseBuilder(
            get(),
            CurrenciesDatabase::class.java,
            "Currencies.db",
        ).build()
    }

    single { get<CurrenciesDatabase>().currencies() }
}
