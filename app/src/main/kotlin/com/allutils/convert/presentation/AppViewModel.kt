package com.allutils.convert.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.allutils.convert.domain.IUserPreferencesRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AppViewModel(
    private val userPreferences: IUserPreferencesRepository
) : ViewModel() {

    private var job: Job? = null
    suspend fun isUserOnboarded(): Boolean {
        return userPreferences.isUserOnboarded()
    }

    fun onUserOnboarded() {
        if (job != null) {
            job?.cancel()
            job = null
        }

        job = viewModelScope.launch {
            userPreferences.onUserOnboarded()
        }
    }

}
