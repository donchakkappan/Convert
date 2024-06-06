package com.allutils.feature_currency.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.allutils.base.nav.NavManager
import com.allutils.base.presentation.viewmodel.BaseAction
import com.allutils.base.presentation.viewmodel.BaseState
import com.allutils.base.presentation.viewmodel.BaseViewModel
import com.allutils.base.result.Result
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import com.allutils.feature_currency.domain.usecase.GetConversionRates
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class CurrencyListViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val navManager: NavManager,
    private val getConversionRatesUseCase: GetConversionRates,
) : BaseViewModel<CurrencyListViewModel.UiState, CurrencyListViewModel.Action>(UiState.Loading) {

    companion object {
        const val DEFAULT_QUERY_NAME = "USD"
        private const val SAVED_QUERY_KEY = "query"
    }

    fun onEnter(
        query: String? = (savedStateHandle.get(SAVED_QUERY_KEY) as? String) ?: DEFAULT_QUERY_NAME
    ) {
        getConversionRates(query ?: "USD")
    }

    private var job: Job? = null

    private fun getConversionRates(query: String) {
        if (job != null) {
            job?.cancel()
            job = null
        }

        savedStateHandle[SAVED_QUERY_KEY] = query

        job = viewModelScope.launch {
            getConversionRatesUseCase(query).also { result ->
                val action = when (result) {
                    is Result.Success -> {
                        if (result.value.isEmpty()) {
                            Action.ConversionRatesLoadFailure
                        } else {
                            Action.ConversionRatesLoadSuccess(result.value)
                        }
                    }

                    is Result.Failure -> {
                        Action.ConversionRatesLoadFailure
                    }
                }
                sendAction(action)
            }
        }
    }

    fun onConversionRateClick(conversionRatesOutput: ConversionRatesOutput) {

    }

    internal sealed interface Action : BaseAction<UiState> {
        class ConversionRatesLoadSuccess(private val conversionRates: List<ConversionRatesOutput>) :
            Action {
            override fun reduce(state: UiState) = UiState.Content(conversionRates)
        }

        object ConversionRatesLoadFailure : Action {
            override fun reduce(state: UiState) = UiState.Error
        }
    }

    @Immutable
    internal sealed interface UiState : BaseState {
        data class Content(val conversionRates: List<ConversionRatesOutput>) : UiState
        object Loading : UiState
        object Error : UiState
    }
}