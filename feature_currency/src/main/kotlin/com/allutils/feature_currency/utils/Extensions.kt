package com.allutils.feature_currency.utils

import java.util.Currency
import java.util.Locale

fun getLocalCurrencyCode(): String {
    val locale: Locale = Locale.getDefault()
    val currency = Currency.getInstance(locale)
    return currency.currencyCode
}
