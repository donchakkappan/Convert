package com.allutils.feature_currency.domain.models.output

internal data class ConversionRatesOutput(
    val currencyCode: String,
    val rate: Double,
    val baseCode: String,
    val isFavorite: Boolean? = false
)
