package com.allutils.feature_currency.domain.usecase

import com.allutils.base.result.Result
import com.allutils.feature_currency.domain.ICurrenciesRepository
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput

internal class GetAllConversionRates(private val currenciesRepository: ICurrenciesRepository) {

    suspend operator fun invoke(baseCode: String): Result<List<ConversionRatesOutput>> {
        val result = currenciesRepository
            .getConversionRates(baseCode)

        return result
    }
}
