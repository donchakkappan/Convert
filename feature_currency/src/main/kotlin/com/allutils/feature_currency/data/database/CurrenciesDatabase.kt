package com.allutils.feature_currency.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.allutils.feature_currency.data.database.models.entities.ConversionRateEntity
import com.allutils.feature_currency.data.database.models.entities.ConversionRateMetaDataEntity

@Database(
    entities = [ConversionRateEntity::class,
        ConversionRateMetaDataEntity::class],
    version = 1,
    exportSchema = false
)
internal abstract class CurrenciesDatabase : RoomDatabase() {

    abstract fun currencies(): CurrenciesDao
}
