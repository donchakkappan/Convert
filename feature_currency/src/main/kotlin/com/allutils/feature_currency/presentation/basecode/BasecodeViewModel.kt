package com.allutils.feature_currency.presentation.basecode

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.allutils.base.presentation.viewmodel.BaseAction
import com.allutils.base.presentation.viewmodel.BaseState
import com.allutils.base.presentation.viewmodel.BaseViewModel
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import com.allutils.feature_currency.domain.usecase.GetAllConversionRates
import com.allutils.feature_currency.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class BasecodeViewModel(
    private val getAllConversionRates: GetAllConversionRates
) : BaseViewModel<BasecodeViewModel.UiState, BasecodeViewModel.Action>(UiState.Loading) {

    private var job: Job? = null

    /**
     * Check is there any favorite conversion
     * If yes pick those conversions
     * If show conversion rate of local currency
     */
    fun loadBasecodeList() {
        if (job != null) {
            job?.cancel()
            job = null
        }

        job = viewModelScope.launch {
            getAllConversionRates.invoke("USD").also { result ->
                result.collectLatest {
                    val action = when (it) {
                        is Resource.Success -> {
                            if (it.data?.isEmpty() == true) {
                                Action.BasecodeListFailure
                            } else {
                                Action.BasecodeListLoadSuccess(it.data!!, 1.0)
                            }
                        }

                        is Resource.Error -> {
                            Action.BasecodeListFailure
                        }

                        else -> {
                            Action.BasecodeListFailure
                        }
                    }
                    sendAction(action)
                }
            }
        }
    }

    internal sealed interface Action : BaseAction<UiState> {
        class BasecodeListLoadSuccess(
            private val conversionRates: List<ConversionRatesOutput>,
            private val amount: Double
        ) :
            Action {
            override fun reduce(state: UiState) = UiState.Content(conversionRates, amount)
        }

        object BasecodeListFailure : Action {
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
