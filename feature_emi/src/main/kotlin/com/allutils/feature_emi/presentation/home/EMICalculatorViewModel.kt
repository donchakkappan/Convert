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
    BaseViewModel<EmiViewState, EmiResults>(
        EmiViewState.EmiDetailsInitialContent(
            EmiDetailsOutput(
                principal = "",
                interest = "",
                tenure = "",
                emi = ""
            )
        )
    ) {

    private var job: Job? = null

    fun processIntent(intent: EmiIntents) {
        when (intent) {
            is EmiIntents.CalculateEMIDetails -> getEMIDetails(intent.emiDetailsInput)

            EmiIntents.LoadInitialValues -> {
                sendAction(EmiResults.EmiDetailsInitialContentSuccess(getUserUpdates()))
            }

            is EmiIntents.UserUpdates -> {
                val emiInput = getUserUpdates(
                    principle = intent.principle,
                    interest = intent.interest,
                    tenure = intent.tenure,
                    emi = intent.emi
                )
                sendAction(EmiResults.UserUpdateSuccess(emiInput))
            }
        }
    }

    private fun getUserUpdates(
        principle: String? = null,
        interest: String? = null,
        tenure: String? = null,
        emi: String? = null
    ): EmiDetailsOutput {
        uiStateFlow.value.let { state ->
            when (state) {
                is EmiViewState.EmiDetailsInitialContent -> {
                    return EmiDetailsOutput(
                        principal = principle ?: state.emiDetails.principal,
                        interest = interest ?: state.emiDetails.interest,
                        tenure = tenure ?: state.emiDetails.tenure,
                        emi = emi ?: state.emiDetails.emi
                    )
                }

                is EmiViewState.UserUpdateContent -> {
                    return EmiDetailsOutput(
                        principal = principle ?: state.emiDetails.principal,
                        interest = interest ?: state.emiDetails.interest,
                        tenure = tenure ?: state.emiDetails.tenure,
                        emi = emi ?: state.emiDetails.emi
                    )
                }

                is EmiViewState.EmiDetailsContent -> {
                    return EmiDetailsOutput(
                        principal = principle ?: state.emiDetails.principal,
                        interest = interest ?: state.emiDetails.interest,
                        tenure = tenure ?: state.emiDetails.tenure,
                        emi = emi ?: state.emiDetails.emi
                    )
                }
            }
        }
    }

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
