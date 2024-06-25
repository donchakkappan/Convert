package com.allutils.feature_currency.domain.usecase

import com.allutils.feature_currency.domain.ICurrenciesRepository

internal class AnyFavoriteConversion(
    private val currenciesRepo: ICurrenciesRepository
) {

    suspend operator fun invoke(): Boolean {
        return currenciesRepo.isThereAnyFavorite()
    }
}
