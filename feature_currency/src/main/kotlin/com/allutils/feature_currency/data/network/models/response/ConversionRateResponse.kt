package com.allutils.feature_currency.data.network.models.response

import com.allutils.feature_currency.data.database.models.entities.ConversionRateEntity
import com.allutils.feature_currency.data.database.models.entities.ConversionRateMetaDataEntity
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConversionRateResponse(

    @SerializedName("base_code")
    @SerialName("base_code")
    val baseCode: String?,
    @SerializedName("conversion_rates")
    @SerialName("conversion_rates")
    val conversionRates: Map<String, Double>?,
    @SerializedName("documentation")
    @SerialName("documentation")
    val documentation: String?,
    @SerializedName("result")
    @SerialName("result")
    val result: String?,
    @SerializedName("terms_of_use")
    @SerialName("terms_of_use")
    val termsOfUse: String?,
    @SerializedName("time_last_update_unix")
    @SerialName("time_last_update_unix")
    val timeLastUpdateUnix: Int?,
    @SerializedName("time_last_update_utc")
    @SerialName("time_last_update_utc")
    val timeLastUpdateUtc: String?,
    @SerializedName("time_next_update_unix")
    @SerialName("time_next_update_unix")
    val timeNextUpdateUnix: Int?,
    @SerializedName("time_next_update_utc")
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
