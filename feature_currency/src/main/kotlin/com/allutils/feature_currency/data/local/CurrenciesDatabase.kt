package com.allutils.feature_currency.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.allutils.feature_currency.data.local.models.entities.ConversionRateEntity
import com.allutils.feature_currency.data.local.models.entities.ConversionRateMetaDataEntity

@Database(
    entities = [ConversionRateEntity::class,
        ConversionRateMetaDataEntity::class],
    version = 1,
    exportSchema = false
)
internal abstract class CurrenciesDatabase : RoomDatabase() {

    abstract fun currencies(): CurrenciesDao
}