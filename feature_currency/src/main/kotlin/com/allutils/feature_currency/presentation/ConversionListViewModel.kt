package com.allutils.feature_currency.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.allutils.base.presentation.viewmodel.BaseAction
import com.allutils.base.presentation.viewmodel.BaseState
import com.allutils.base.presentation.viewmodel.BaseViewModel
import com.allutils.base.result.Result
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import com.allutils.feature_currency.domain.usecase.GetAllConversionRates
import com.allutils.feature_currency.domain.usecase.GetFavoriteConversionRates
import com.allutils.feature_currency.utils.Resource
import com.allutils.feature_currency.utils.getLocalCurrencyCode
import com.google.firebase.perf.metrics.AddTrace
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class ConversionListViewModel(
    private val getAllConversionRatesUseCase: GetAllConversionRates,
    private val favoriteConversionRates: GetFavoriteConversionRates,
) : BaseViewModel<ConversionListViewModel.UiState, ConversionListViewModel.Action>(UiState.Loading) {

    var amount = 1.0
    var baseCode = "USD"

    private var job: Job? = null

    /**
     * Check is there any favorite conversion
     * If yes pick those conversions
     * If show conversion rate of local currency
     */
    fun showConversionRates() {
        if (job != null) {
            job?.cancel()
            job = null
        }

        job = viewModelScope.launch {
            favoriteConversionRates.invoke(baseCode, getLocalCurrencyCode()).also { result ->
                result.collectLatest {
                    val action = when (it) {
                        is Resource.Success -> {
                            if (it.data?.isEmpty() == true) {
                                Action.ConversionRatesLoadFailure
                            } else {
                                Action.ConversionRatesLoadSuccess(it.data!!, amount)
                            }
                        }

                        is Resource.Error -> {
                            Action.ConversionRatesLoadFailure
                        }

                        else -> {
                            Action.ConversionRatesLoadFailure
                        }
                    }
                    sendAction(action)
                }
            }
        }
    }

    @AddTrace(name = "GET Conversion Rates Trace", enabled = true)
    fun getConversionRates() {
        if (job != null) {
            job?.cancel()
            job = null
        }

        job = viewModelScope.launch {
            getAllConversionRatesUseCase.invoke(baseCode).also { result ->
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
