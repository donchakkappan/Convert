package com.allutils.feature_emi.presentation.home.model

import com.allutils.feature_emi.domain.models.EmiDetailsInput

sealed class EmiIntents {

    data class CalculateEMIDetails(val emiDetailsInput: EmiDetailsInput) : EmiIntents()

    data class UserUpdates(
        val selected: String = "E",
        val principle: String? = null,
        val interest: String? = null,
        val tenure: String? = null,
        val emi: String? = null
    ) : EmiIntents()

    data object Reset : EmiIntents()

}
