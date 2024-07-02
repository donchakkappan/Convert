package com.allutils.feature_emi.presentation.home

import androidx.compose.runtime.Immutable
import com.allutils.base.presentation.viewmodel.BaseAction
import com.allutils.base.presentation.viewmodel.BaseState
import com.allutils.base.presentation.viewmodel.BaseViewModel

internal class EMICalculatorViewModel() : BaseViewModel<EMICalculatorViewModel.UiState, EMICalculatorViewModel.Action>(UiState.Loading) {

    internal sealed interface Action : BaseAction<UiState> {
        class LoadSuccess(
            private val amount: Double
        ) :
            Action {
            override fun reduce(state: UiState) = UiState.FavoriteContent(amount)
        }

        object LoadFailure : Action {
            override fun reduce(state: UiState) = UiState.Error
        }

        object Loading : Action {
            override fun reduce(state: UiState) = UiState.Loading
        }
    }

    @Immutable
    internal sealed interface UiState : BaseState {
        data class FavoriteContent(val amount: Double) :
            UiState

        data class LocalContent(val amount: Double) :
            UiState

        object Loading : UiState
        object Error : UiState
    }
}
