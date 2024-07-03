package com.allutils.feature_emi.presentation.home.model

import com.allutils.feature_emi.domain.models.EmiDetailsInput

sealed class EmiIntents {

    data object LoadInitialValues : EmiIntents()

    data class CalculateEMIDetails(val emiDetailsInput: EmiDetailsInput) : EmiIntents()

    data class UserUpdates(val principle: String) : EmiIntents()
}
