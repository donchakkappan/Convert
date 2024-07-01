package com.allutils.feature_currency.domain

import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import com.allutils.feature_currency.utils.Resource
import kotlinx.coroutines.flow.Flow

internal interface ICurrenciesRepository {

    suspend fun getConversionRates(baseCode: String): Flow<Resource<List<ConversionRatesOutput>>>

    suspend fun getFavoriteConversionRates(baseCode: String): Flow<Resource<List<ConversionRatesOutput>>>

    suspend fun getLocalConversionRate(baseCode: String, localCurrencyCode: String): Flow<Resource<List<ConversionRatesOutput>>>

    suspend fun isThereAnyFavorite(): Boolean

    suspend fun markFavoriteAndGetAll(baseCode: String, favoriteCode: String): Flow<Resource<List<ConversionRatesOutput>>>

    suspend fun getLastUpdatedTime(): String
}
