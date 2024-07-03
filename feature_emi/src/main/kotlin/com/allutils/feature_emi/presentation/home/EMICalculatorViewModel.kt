package com.allutils.feature_emi.presentation.home

import androidx.lifecycle.viewModelScope
import com.allutils.base.presentation.viewmodel.BaseViewModel
import com.allutils.feature_emi.domain.models.EmiDetailsInput
import com.allutils.feature_emi.domain.models.EmiDetailsOutput
import com.allutils.feature_emi.domain.usecase.GetEmiDetails
import com.allutils.feature_emi.presentation.home.model.EmiIntents
import com.allutils.feature_emi.presentation.home.model.EmiResults
import com.allutils.feature_emi.presentation.home.model.EmiViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class EMICalculatorViewModel(private val getEmiDetails: GetEmiDetails) :
    BaseViewModel<EmiViewState, EmiResults>(EmiViewState.EmiDetailsInitialContent) {

    private var job: Job? = null

    fun processIntent(intent: EmiIntents) {
        when (intent) {
            is EmiIntents.CalculateEMIDetails -> getEMIDetails(intent.emiDetailsInput)

            EmiIntents.LoadInitialValues -> {
                sendAction(EmiResults.EmiDetailsInitialContentSuccess)
            }

            is EmiIntents.UserUpdates -> {
                sendAction(EmiResults.UserUpdateSuccess(getUserUpdates(intent.principle)))
            }
        }
    }

    private fun getUserUpdates(principal: String) = EmiDetailsOutput(
        principal = principal,
        interest = principal,
        tenure = principal,
        emi = principal
    )

    private fun getEMIDetails(emiDetailsInput: EmiDetailsInput) {
        if (job != null) {
            job?.cancel()
            job = null
        }

        job = viewModelScope.launch {
            val emiDetails = getEmiDetails.invoke(emiDetailsInput)
            sendAction(EmiResults.EmiDetailsSuccess(emiDetails))
        }
    }

}
