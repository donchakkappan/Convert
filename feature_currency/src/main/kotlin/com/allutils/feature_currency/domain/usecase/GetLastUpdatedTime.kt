package com.allutils.feature_currency.domain.usecase

import com.allutils.feature_currency.domain.ICurrenciesRepository

internal class GetLastUpdatedTime(
    private val currenciesRepo: ICurrenciesRepository
) {
    suspend operator fun invoke(baseCode: String): String {
        return currenciesRepo.getLastUpdatedTime(baseCode)
    }
}
