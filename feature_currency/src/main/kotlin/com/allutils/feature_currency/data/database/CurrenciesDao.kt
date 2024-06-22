package com.allutils.feature_currency.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.allutils.feature_currency.data.database.models.entities.ConversionRateEntity
import com.allutils.feature_currency.data.database.models.entities.ConversionRateMetaDataEntity

@Dao
internal interface CurrenciesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(response: ConversionRateMetaDataEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversionRates(conversionRates: List<ConversionRateEntity>)

    @Query("SELECT * FROM conversion_rate WHERE baseCode = :baseCode")
    suspend fun getRates(baseCode: String): List<ConversionRateEntity>

}
