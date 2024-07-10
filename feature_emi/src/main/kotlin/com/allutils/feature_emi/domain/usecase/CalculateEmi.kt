package com.allutils.feature_emi.domain.usecase

import com.allutils.feature_emi.domain.calculateEMI
import com.allutils.feature_emi.domain.models.EmiDetailsInput
import com.allutils.feature_emi.domain.models.EmiDetailsOutput

internal class CalculateEmi() {
    suspend operator fun invoke(emiDetailsInput: EmiDetailsInput): EmiDetailsOutput {
        return EmiDetailsOutput(
            selected = emiDetailsInput.selected,
            principal = emiDetailsInput.principal.toString(),
            interest = emiDetailsInput.interest.toString(),
            tenure = emiDetailsInput.tenure.toString(),
            emi = calculateEMI(
                principal = emiDetailsInput.principal ?: 0,
                tenure = emiDetailsInput.tenure ?: 0,
                rate = emiDetailsInput.interest ?: 0.0
            ).toString()
        )
    }
}
