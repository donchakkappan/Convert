package com.allutils.feature_currency.data.network.models.response

import com.allutils.feature_currency.data.local.models.entities.ConversionRateEntity
import com.allutils.feature_currency.data.local.models.entities.ConversionRateMetaDataEntity
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConversionRateResponse(
    @SerialName("base_code")
    val baseCode: String?,
    @SerialName("conversion_rates")
    val conversionRates: Map<String, Double>?,
    @SerialName("documentation")
    val documentation: String?,
    @SerialName("result")
    val result: String?,
    @SerialName("terms_of_use")
    val termsOfUse: String?,
    @SerialName("time_last_update_unix")
    val timeLastUpdateUnix: Int?,
    @SerialName("time_last_update_utc")
    val timeLastUpdateUtc: String?,
    @SerialName("time_next_update_unix")
    val timeNextUpdateUnix: Int?,
    @SerialName("time_next_update_utc")
    val timeNextUpdateUtc: String?
)

internal fun ConversionRateResponse.toMetaDataEntityModel() =
    ConversionRateMetaDataEntity(
        baseCode = this.baseCode ?: "",
        documentation = this.documentation ?: "",
        result = this.result ?: "",
        termsOfUse = this.termsOfUse ?: "",
        timeLastUpdateUnix = this.timeLastUpdateUnix ?: 0,
        timeLastUpdateUtc = this.timeLastUpdateUtc ?: "",
        timeNextUpdateUnix = this.timeNextUpdateUnix ?: 0,
        timeNextUpdateUtc = this.timeNextUpdateUtc ?: "",
    )

internal fun ConversionRateResponse.toConversionRatesEntityModel(): List<ConversionRateEntity> {
    return this.conversionRates?.map {
        ConversionRateEntity(
            baseCode = this.baseCode ?: "USD",
            currencyCode = it.key,
            rate = it.value
        )
    } ?: emptyList()
}

internal fun ConversionRateResponse.toDomainModel(): List<ConversionRatesOutput> {
    return this.conversionRates?.map {
        ConversionRatesOutput(
            baseCode = this.baseCode ?: "USD",
            currencyCode = it.key,
            rate = it.value
        )
    } ?: emptyList()
}