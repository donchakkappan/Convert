package com.allutils.feature_currency.domain.usecase

import com.allutils.base.result.Result
import com.allutils.feature_currency.domain.ICurrenciesRepository
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput

internal class GetFavoriteConversionRates(
    private val currenciesRepo: ICurrenciesRepository
) {

    suspend operator fun invoke(baseCode: String, localCurrencyCode: String): Result<List<ConversionRatesOutput>> {
        return if (currenciesRepo.isThereAnyFavorite()) {
            currenciesRepo.getFavoriteConversionRates(baseCode)
        } else {
            currenciesRepo.getLocalConversionRate(baseCode, localCurrencyCode)
        }
    }
}
