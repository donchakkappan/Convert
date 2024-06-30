package com.allutils.feature_currency.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.allutils.feature_currency.data.store.CurrencyPreferences.Companion.CURRENCY_DATA_STORE
import com.allutils.feature_currency.data.store.CurrencyPreferences.PreferencesKeys.KEY_BASE_CODE_SELECTED
import com.allutils.feature_currency.domain.ICurrencyPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = CURRENCY_DATA_STORE)

class CurrencyPreferences(private val context: Context) : ICurrencyPreferences {

    override suspend fun getSelectedBasecode(): String = context.dataStore.data.map { preferences ->
        preferences[KEY_BASE_CODE_SELECTED] ?: DEFAULT_BASE_CODE
    }.first()

    override suspend fun updateBasecodeSelection(basecode: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_BASE_CODE_SELECTED] = basecode
        }
    }

    override suspend fun clearPreferences() {
        context.dataStore.edit {
            it.clear()
        }
    }

    private object PreferencesKeys {
        val KEY_BASE_CODE_SELECTED = stringPreferencesKey(NAME_BASE_CODE_SELECTED)
    }

    companion object {
        const val CURRENCY_DATA_STORE = "CURRENCY_DATA_STORE"
        private const val NAME_BASE_CODE_SELECTED = "NAME_BASE_CODE_SELECTED"
        private const val DEFAULT_BASE_CODE = "USD"
    }
}
