package com.allutils.feature_currency.presentation.home

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.allutils.base.presentation.viewmodel.BaseAction
import com.allutils.base.presentation.viewmodel.BaseState
import com.allutils.base.presentation.viewmodel.BaseViewModel
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import com.allutils.feature_currency.domain.usecase.AnyFavoriteConversion
import com.allutils.feature_currency.domain.usecase.GetFavoriteConversionRates
import com.allutils.feature_currency.domain.usecase.MarkFavoriteAndGetConversionRates
import com.allutils.feature_currency.utils.Resource
import com.allutils.feature_currency.utils.getLocalCurrencyCode
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class ConversionListViewModel(
    private val markFavoriteAndGetConversionRates: MarkFavoriteAndGetConversionRates,
    private val favoriteConversionRates: GetFavoriteConversionRates,
    private val favoriteConversion: AnyFavoriteConversion
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
                                if (favoriteConversion.invoke())
                                    Action.FavoriteConversionRatesLoadSuccess(it.data!!, amount)
                                else
                                    Action.LocalConversionRatesLoadSuccess(it.data!!, amount)
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

    fun markFavoriteAndGetAll(favoriteCode: String) {

        if (job != null) {
            job?.cancel()
            job = null
        }

        job = viewModelScope.launch {
            markFavoriteAndGetConversionRates.invoke(baseCode, favoriteCode).also { result ->
                result.collectLatest {
                    val action = when (it) {
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

                        else -> {
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
