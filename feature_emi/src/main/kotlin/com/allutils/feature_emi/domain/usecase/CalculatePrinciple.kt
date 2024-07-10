package com.allutils.feature_emi.domain.usecase

import com.allutils.feature_emi.domain.calculatePrincipal
import com.allutils.feature_emi.domain.models.EmiDetailsInput
import com.allutils.feature_emi.domain.models.EmiDetailsOutput

internal class CalculatePrinciple() {
    suspend operator fun invoke(emiDetailsInput: EmiDetailsInput): EmiDetailsOutput {
        return EmiDetailsOutput(
            selected = emiDetailsInput.selected,
            principal = calculatePrincipal(
                emi = emiDetailsInput.emi ?: 0,
                tenure = emiDetailsInput.tenure ?: 0,
                rate = emiDetailsInput.interest ?: 0.0
            ).toString(),
            interest = emiDetailsInput.interest.toString(),
            tenure = emiDetailsInput.tenure.toString(),
            emi = emiDetailsInput.emi.toString()
        )
    }
}
