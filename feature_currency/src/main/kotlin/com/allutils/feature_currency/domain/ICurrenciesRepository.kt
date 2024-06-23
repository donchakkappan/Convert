package com.allutils.feature_currency.domain

import com.allutils.base.result.Result
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput

internal interface ICurrenciesRepository {

    suspend fun getConversionRates(baseCode: String): Result<List<ConversionRatesOutput>>

    suspend fun getFavoriteConversionRates(baseCode: String): Result<List<ConversionRatesOutput>>

    suspend fun getLocalConversionRate(baseCode: String, localCurrencyCode: String): Result<List<ConversionRatesOutput>>

    suspend fun isThereAnyFavorite(): Boolean
}
