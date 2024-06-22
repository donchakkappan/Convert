package com.allutils.convert.domain

interface IUserPreferencesRepository {

    suspend fun isUserOnboarded(): Boolean

    suspend fun onUserOnboarded()

    suspend fun clearPreferences()
}
