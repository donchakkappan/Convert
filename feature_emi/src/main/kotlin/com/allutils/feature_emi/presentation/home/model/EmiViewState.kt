package com.allutils.feature_emi.presentation.home.model

import com.allutils.base.presentation.viewmodel.BaseState
import com.allutils.feature_emi.domain.models.EmiDetailsOutput

internal sealed interface EmiViewState : BaseState {

    data object EmiDetailsInitialContent : EmiViewState

    data class EmiDetailsContent(val emiDetails: EmiDetailsOutput) : EmiViewState

    data class UserUpdateContent(val emiDetails: EmiDetailsOutput) : EmiViewState
}
