package com.allutils.feature_emi.presentation.home.model

import com.allutils.base.presentation.viewmodel.BaseAction
import com.allutils.feature_emi.domain.models.EmiDetailsOutput

internal sealed interface EmiResults : BaseAction<EmiViewState> {

    class EmiDetailsSuccess(private val emiDetails: EmiDetailsOutput) : EmiResults {
        override fun reduce(state: EmiViewState) = EmiViewState.EmiDetailsContent(emiDetails)
    }

    class UserUpdateSuccess(private val emiDetails: EmiDetailsOutput) : EmiResults {
        override fun reduce(state: EmiViewState) = EmiViewState.UserUpdateContent(emiDetails)
    }

}
