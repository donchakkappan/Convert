package com.allutils.feature_currency.domain

interface ICurrencyPreferences {

    suspend fun getSelectedBasecode(): String

    suspend fun updateBasecodeSelection(basecode: String)

    suspend fun clearPreferences()
}
