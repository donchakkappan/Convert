package com.allutils.feature_emi.presentation.home

import androidx.lifecycle.viewModelScope
import com.allutils.base.presentation.viewmodel.BaseViewModel
import com.allutils.feature_emi.domain.models.EmiDetailsInput
import com.allutils.feature_emi.domain.models.EmiDetailsOutput
import com.allutils.feature_emi.domain.usecase.CalculateEmi
import com.allutils.feature_emi.domain.usecase.CalculateInterest
import com.allutils.feature_emi.domain.usecase.CalculatePrinciple
import com.allutils.feature_emi.domain.usecase.CalculateTenure
import com.allutils.feature_emi.presentation.home.model.EmiIntents
import com.allutils.feature_emi.presentation.home.model.EmiResults
import com.allutils.feature_emi.presentation.home.model.EmiViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class EMICalculatorViewModel(
    private val calculateEmi: CalculateEmi,
    private val calculateTenure: CalculateTenure,
    private val calculateInterest: CalculateInterest,
    private val calculatePrinciple: CalculatePrinciple
) :
    BaseViewModel<EmiViewState, EmiResults>(
        EmiViewState.UserUpdateContent(
            EmiDetailsOutput(
                selected = "E",
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
            is EmiIntents.CalculateEMIDetails -> {
                when (intent.emiDetailsInput.selected) {
                    "P" -> {
                        getPrincipleDetails(intent.emiDetailsInput)
                    }

                    "I" -> {
                        getInterestDetails(intent.emiDetailsInput)
                    }

                    "T" -> {
                        getTenureDetails(intent.emiDetailsInput)
                    }

                    else -> {
                        getEMIDetails(intent.emiDetailsInput)
                    }
                }
            }

            is EmiIntents.UserUpdates -> {
                val emiInput = getUserUpdates(
                    selected = intent.selected,
                    principle = intent.principle,
                    interest = intent.interest,
                    tenure = intent.tenure,
                    emi = intent.emi
                )
                sendAction(EmiResults.UserUpdateSuccess(emiInput))
            }

            EmiIntents.Reset -> {
                val emiOutput = EmiDetailsOutput(
                    selected = "E",
                    principal = "",
                    interest = "",
                    tenure = "",
                    emi = ""
                )
                sendAction(EmiResults.UserUpdateSuccess(emiOutput))
            }
        }
    }

    private fun getUserUpdates(
        selected: String,
        principle: String? = null,
        interest: String? = null,
        tenure: String? = null,
        emi: String? = null
    ): EmiDetailsOutput {
        uiStateFlow.value.let { state ->
            when (state) {

                is EmiViewState.UserUpdateContent -> {
                    return EmiDetailsOutput(
                        selected = selected,
                        principal = principle ?: state.emiDetails.principal,
                        interest = interest ?: state.emiDetails.interest,
                        tenure = tenure ?: state.emiDetails.tenure,
                        emi = emi ?: state.emiDetails.emi
                    )
                }

                is EmiViewState.EmiDetailsContent -> {
                    return EmiDetailsOutput(
                        selected = selected,
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
            val emiDetails = calculateEmi.invoke(emiDetailsInput)
            sendAction(EmiResults.EmiDetailsSuccess(emiDetails))
        }
    }

    private fun getTenureDetails(emiDetailsInput: EmiDetailsInput) {
        if (job != null) {
            job?.cancel()
            job = null
        }

        job = viewModelScope.launch {
            val emiDetails = calculateTenure.invoke(emiDetailsInput)
            sendAction(EmiResults.EmiDetailsSuccess(emiDetails))
        }
    }

    private fun getPrincipleDetails(emiDetailsInput: EmiDetailsInput) {
        if (job != null) {
            job?.cancel()
            job = null
        }

        job = viewModelScope.launch {
            val emiDetails = calculatePrinciple.invoke(emiDetailsInput)
            sendAction(EmiResults.EmiDetailsSuccess(emiDetails))
        }
    }

    private fun getInterestDetails(emiDetailsInput: EmiDetailsInput) {
        if (job != null) {
            job?.cancel()
            job = null
        }

        job = viewModelScope.launch {
            val emiDetails = calculateInterest.invoke(emiDetailsInput)
            sendAction(EmiResults.EmiDetailsSuccess(emiDetails))
        }
    }

}
