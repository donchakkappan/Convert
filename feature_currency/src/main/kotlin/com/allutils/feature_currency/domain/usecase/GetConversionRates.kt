package com.allutils.feature_currency.domain.usecase

import com.allutils.base.result.Result
import com.allutils.feature_currency.domain.CurrenciesRepository
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput

internal class GetConversionRates(private val currenciesRepository: CurrenciesRepository) {

    suspend operator fun invoke(baseCode: String): Result<List<ConversionRatesOutput>> {
        val result = currenciesRepository
            .getConversionRates(baseCode)

        return result
    }
}
