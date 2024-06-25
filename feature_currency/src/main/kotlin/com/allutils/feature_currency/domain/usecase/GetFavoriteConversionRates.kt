package com.allutils.feature_currency.domain.usecase

import com.allutils.feature_currency.domain.ICurrenciesRepository
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import com.allutils.feature_currency.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

internal class GetFavoriteConversionRates(
    private val currenciesRepo: ICurrenciesRepository
) {

    suspend operator fun invoke(baseCode: String, localCurrencyCode: String): Flow<Resource<List<ConversionRatesOutput>>> {
        return if (currenciesRepo.isThereAnyFavorite()) {
            currenciesRepo.getFavoriteConversionRates(baseCode).flowOn(Dispatchers.IO)
        } else {
            currenciesRepo.getLocalConversionRate(baseCode, localCurrencyCode).flowOn(Dispatchers.IO)
        }
    }
}
