package com.allutils.convert.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.allutils.convert.data.UserPreferencesRepository.Companion.CONVERT_APP_DATA_STORE
import com.allutils.convert.data.UserPreferencesRepository.PreferencesKeys.KEY_IS_USER_ONBOARDED
import com.allutils.convert.domain.IUserPreferencesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = CONVERT_APP_DATA_STORE)

class UserPreferencesRepository(private val context: Context) : IUserPreferencesRepository {

    override suspend fun isUserOnboarded(): Boolean = context.dataStore.data.map { preferences ->
        preferences[KEY_IS_USER_ONBOARDED] ?: false
    }.first()

    override suspend fun onUserOnboarded() {
        context.dataStore.edit { preferences ->
            preferences[KEY_IS_USER_ONBOARDED] = true
        }
    }

    override suspend fun clearPreferences() {
        context.dataStore.edit {
            it.clear()
        }
    }

    private object PreferencesKeys {
        val KEY_IS_USER_ONBOARDED = booleanPreferencesKey(NAME_IS_USER_ONBOARDED)
    }

    companion object {
        const val CONVERT_APP_DATA_STORE = "CONVERT_APP_DATA_STORE"
        private const val NAME_IS_USER_ONBOARDED = "NAME_IS_USER_ONBOARDED"
    }
}
