package com.allutils.feature_currency.presentation.home

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.allutils.base.presentation.viewmodel.BaseAction
import com.allutils.base.presentation.viewmodel.BaseState
import com.allutils.base.presentation.viewmodel.BaseViewModel
import com.allutils.feature_currency.domain.ICurrencyPreferences
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import com.allutils.feature_currency.domain.usecase.AnyFavoriteConversion
import com.allutils.feature_currency.domain.usecase.GetFavoriteConversionRates
import com.allutils.feature_currency.domain.usecase.MarkFavoriteAndGetConversionRates
import com.allutils.feature_currency.utils.Resource
import com.allutils.feature_currency.utils.getLocalCurrencyCode
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class ConversionListViewModel(
    private val markFavoriteAndGetConversionRates: MarkFavoriteAndGetConversionRates,
    private val favoriteConversionRates: GetFavoriteConversionRates,
    private val favoriteConversion: AnyFavoriteConversion,
    private val currencyPreferences: ICurrencyPreferences
) : BaseViewModel<ConversionListViewModel.UiState, ConversionListViewModel.Action>(UiState.Loading) {

    var amount = 1.0

    private var job: Job? = null

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> get() = _state.asStateFlow()

    fun handleAction(action: MainAction) {
        when (action) {
            is MainAction.LoadPreference -> loadPreference()
            is MainAction.UpdatePreference -> updatePreference(action.newValue)
        }
    }

    private fun loadPreference() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val preferenceValue = getPreferenceValue()
            _state.value = _state.value.copy(preferenceValue = preferenceValue, isLoading = false)
        }
    }

    private suspend fun getPreferenceValue(): String {
        return currencyPreferences.getSelectedBasecode()
    }

    private fun updatePreference(newValue: String) {
        viewModelScope.launch {
            currencyPreferences.updateBasecodeSelection(newValue)
            _state.value = _state.value.copy(preferenceValue = newValue)
        }
    }

    data class MainState(
        val preferenceValue: String = "",
        val isLoading: Boolean = false
    )

    sealed class MainAction {
        object LoadPreference : MainAction()
        data class UpdatePreference(val newValue: String) : MainAction()
    }

    /**
     * Check is there any favorite conversion
     * If yes pick those conversions
     * If show conversion rate of local currency
     */
    fun showConversionRates(baseCode: String? = null) {
        if (job != null) {
            job?.cancel()
            job = null
        }

        job = viewModelScope.launch {
            baseCode?.let {
                handleAction(MainAction.UpdatePreference(it))
            }

            favoriteConversionRates.invoke(baseCode ?: getPreferenceValue(), getLocalCurrencyCode()).also { result ->
                result.collectLatest {
                    val action = when (it) {

                        is Resource.Loading -> {
                            Action.ConversionRatesLoading
                        }

                        is Resource.Success -> {
                            if (it.data?.isEmpty() == true) {
                                Action.ConversionRatesLoadFailure
                            } else {
                                if (favoriteConversion.invoke())
                                    Action.FavoriteConversionRatesLoadSuccess(it.data!!, amount)
                                else
                                    Action.LocalConversionRatesLoadSuccess(it.data!!, amount)
                            }
                        }

                        is Resource.Error -> {
                            Action.ConversionRatesLoadFailure
                        }
                    }
                    sendAction(action)
                }
            }
        }
    }

    fun markFavoriteAndGetAll(favoriteCode: String) {

        if (job != null) {
            job?.cancel()
            job = null
        }

        job = viewModelScope.launch {
            val baseCode = _state.value.preferenceValue
            markFavoriteAndGetConversionRates.invoke(baseCode, favoriteCode).also { result ->
                result.collectLatest {
                    val action = when (it) {
                        is Resource.Loading -> {
                            Action.ConversionRatesLoading
                        }

                        is Resource.Success -> {
                            if (it.data?.isEmpty() == true) {
                                Action.ConversionRatesLoadFailure
                            } else {
                                if (favoriteConversion.invoke())
                                    Action.FavoriteConversionRatesLoadSuccess(it.data!!, amount)
                                else
                                    Action.LocalConversionRatesLoadSuccess(it.data!!, amount)
                            }
                        }

                        is Resource.Error -> {
                            Action.ConversionRatesLoadFailure
                        }
                    }
                    sendAction(action)
                }
            }
        }
    }

    internal sealed interface Action : BaseAction<UiState> {
        class FavoriteConversionRatesLoadSuccess(
            private val conversionRates: List<ConversionRatesOutput>,
            private val amount: Double
        ) :
            Action {
            override fun reduce(state: UiState) = UiState.FavoriteContent(conversionRates, amount)
        }

        class LocalConversionRatesLoadSuccess(
            private val conversionRates: List<ConversionRatesOutput>,
            private val amount: Double
        ) :
            Action {
            override fun reduce(state: UiState) = UiState.LocalContent(conversionRates, amount)
        }

        object ConversionRatesLoadFailure : Action {
            override fun reduce(state: UiState) = UiState.Error
        }

        object ConversionRatesLoading : Action {
            override fun reduce(state: UiState) = UiState.Loading
        }
    }

    @Immutable
    internal sealed interface UiState : BaseState {
        data class FavoriteContent(val conversionRates: List<ConversionRatesOutput>, val amount: Double) :
            UiState

        data class LocalContent(val conversionRates: List<ConversionRatesOutput>, val amount: Double) :
            UiState

        object Loading : UiState
        object Error : UiState
    }
}
