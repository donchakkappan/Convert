package com.allutils.feature_currency.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.allutils.feature_currency.data.database.models.entities.ConversionRateEntity
import com.allutils.feature_currency.data.database.models.entities.ConversionRateMetaDataEntity

@Dao
internal interface CurrenciesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversionRateMetaData(response: ConversionRateMetaDataEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertConversionRates(conversionRates: List<ConversionRateEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertConversionRate(conversionRate: ConversionRateEntity): Long

    @Query(
        """
        UPDATE conversion_rate
        SET rate = :rate
        WHERE baseCode = :baseCode AND currencyCode = :currencyCode"""
    )
    suspend fun updateConversionRate(baseCode: String, currencyCode: String, rate: Double)

    @Transaction
    suspend fun insertBulkConversionRates(conversionRates: List<ConversionRateEntity>) {
        conversionRates.forEach { conversionRate ->
            val isFavorite = isFavorite(conversionRate.currencyCode) ?: false

            val result = insertConversionRate(conversionRate)
            if (result == -1L) {
                updateConversionRate(
                    conversionRate.baseCode,
                    conversionRate.currencyCode,
                    conversionRate.rate
                )
            } else {
                updateFavoriteStatus(
                    conversionRate.baseCode,
                    conversionRate.currencyCode,
                    isFavorite
                )
            }
        }
    }

    @Query(
        """
        UPDATE conversion_rate
        SET isFavorite = :isFavorite
        WHERE baseCode = :baseCode AND currencyCode = :currencyCode"""
    )
    suspend fun updateFavoriteStatus(baseCode: String, currencyCode: String, isFavorite: Boolean)

    @Query("SELECT * FROM conversion_rate WHERE baseCode = :baseCode")
    suspend fun getAllConversionRates(baseCode: String): List<ConversionRateEntity>

    @Query("SELECT isFavorite FROM conversion_rate WHERE currencyCode = :currencyCode")
    suspend fun isFavorite(currencyCode: String): Boolean?

    @Query("SELECT * FROM conversion_rate WHERE baseCode = :baseCode AND isFavorite = 1")
    suspend fun getFavoriteConversionRates(baseCode: String): List<ConversionRateEntity>

    @Query("UPDATE conversion_rate SET isFavorite = 1 WHERE currencyCode = :currencyCode")
    suspend fun markAsFavoriteConversion(currencyCode: String)

    @Query("UPDATE conversion_rate SET isFavorite = 0 WHERE currencyCode = :currencyCode")
    suspend fun deleteFavoriteConversion(currencyCode: String)

    @Query("SELECT COUNT(*) FROM conversion_rate WHERE isFavorite = 1")
    suspend fun hasFavoriteItem(): Int

    @Query("SELECT * FROM conversion_rate WHERE baseCode = :baseCode AND currencyCode = :currencyCode")
    suspend fun getLocalConversionRate(baseCode: String, currencyCode: String): List<ConversionRateEntity>

    @Query("SELECT timeLastUpdateUnix FROM conversion_rate_metadata")
    suspend fun getLastUpdatedTime(): Long
}
