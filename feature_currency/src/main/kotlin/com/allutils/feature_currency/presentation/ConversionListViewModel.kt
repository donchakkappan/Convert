package com.allutils.feature_currency.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.allutils.base.presentation.viewmodel.BaseAction
import com.allutils.base.presentation.viewmodel.BaseState
import com.allutils.base.presentation.viewmodel.BaseViewModel
import com.allutils.base.result.Result
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import com.allutils.feature_currency.domain.usecase.GetConversionRates
import com.google.firebase.perf.metrics.AddTrace
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class ConversionListViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val getConversionRatesUseCase: GetConversionRates,
) : BaseViewModel<ConversionListViewModel.UiState, ConversionListViewModel.Action>(UiState.Loading) {

    var amount = 1.0
    var baseCode = "USD"

    companion object {
        const val DEFAULT_QUERY_NAME = "USD"
        private const val SAVED_QUERY_KEY = "query"
    }

    fun onEnter(
        query: String? = (savedStateHandle.get(SAVED_QUERY_KEY) as? String) ?: DEFAULT_QUERY_NAME
    ) {
        getConversionRates()
    }

    private var job: Job? = null

    @AddTrace(name = "GET Conversion Rates Trace", enabled = true)
    fun getConversionRates() {
        if (job != null) {
            job?.cancel()
            job = null
        }

        savedStateHandle[SAVED_QUERY_KEY] = baseCode

        job = viewModelScope.launch {
            getConversionRatesUseCase.invoke(baseCode).also { result ->
                val action = when (result) {
                    is Result.Success -> {
                        if (result.value.isEmpty()) {
                            Action.ConversionRatesLoadFailure
                        } else {
                            Action.ConversionRatesLoadSuccess(result.value, amount)
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

    internal sealed interface Action : BaseAction<UiState> {
        class ConversionRatesLoadSuccess(
            private val conversionRates: List<ConversionRatesOutput>,
            private val amount: Double
        ) :
            Action {
            override fun reduce(state: UiState) = UiState.Content(conversionRates, amount)
        }

        object ConversionRatesLoadFailure : Action {
            override fun reduce(state: UiState) = UiState.Error
        }
    }

    @Immutable
    internal sealed interface UiState : BaseState {
        data class Content(val conversionRates: List<ConversionRatesOutput>, val amount: Double) :
            UiState

        object Loading : UiState
        object Error : UiState
    }
}
