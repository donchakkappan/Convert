package com.allutils.feature_currency.data.database.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput

@Entity(tableName = "conversion_rate_metadata")
data class ConversionRateMetaDataEntity(
    @PrimaryKey val baseCode: String,
    val documentation: String,
    val result: String,
    val termsOfUse: String,
    val timeLastUpdateUnix: Int,
    val timeLastUpdateUtc: String,
    val timeNextUpdateUnix: Int,
    val timeNextUpdateUtc: String
)

@Entity(
    tableName = "conversion_rate",
    primaryKeys = ["baseCode", "currencyCode"],
    foreignKeys = [ForeignKey(
        entity = ConversionRateMetaDataEntity::class,
        parentColumns = ["baseCode"],
        childColumns = ["baseCode"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ConversionRateEntity(
    @ColumnInfo(name = "baseCode") val baseCode: String,
    @ColumnInfo(name = "currencyCode") val currencyCode: String,
    @ColumnInfo(name = "rate") val rate: Double,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean? = false
)

internal fun ConversionRateEntity.toDomainModel() =
    ConversionRatesOutput(
        baseCode = this.baseCode,
        currencyCode = this.currencyCode,
        rate = this.rate,
        isFavorite = this.isFavorite
    )
