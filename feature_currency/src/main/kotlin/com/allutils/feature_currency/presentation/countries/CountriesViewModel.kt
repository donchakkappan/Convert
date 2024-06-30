package com.allutils.feature_currency.presentation.countries

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.allutils.base.presentation.viewmodel.BaseAction
import com.allutils.base.presentation.viewmodel.BaseState
import com.allutils.base.presentation.viewmodel.BaseViewModel
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import com.allutils.feature_currency.domain.usecase.GetAllConversionRates
import com.allutils.feature_currency.utils.Resource
import com.google.firebase.perf.metrics.AddTrace
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class CountriesViewModel(
    private val getAllConversionRatesUseCase: GetAllConversionRates
) : BaseViewModel<CountriesViewModel.UiState, CountriesViewModel.Action>(UiState.Loading) {

    var amount = 1.0
    var baseCode = "USD"

    private var job: Job? = null

    @AddTrace(name = "GET Conversion Rates Trace", enabled = true)
    fun getConversionRates() {
        if (job != null) {
            job?.cancel()
            job = null
        }

        job = viewModelScope.launch {
            getAllConversionRatesUseCase.invoke(baseCode).also { result ->
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

    fun markFavoriteItem() {


        //conversionsViewModel.showConversionRates()
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
