package com.allutils.feature_emi.domain.usecase

import com.allutils.feature_emi.domain.calculateInterestRate
import com.allutils.feature_emi.domain.models.EmiDetailsInput
import com.allutils.feature_emi.domain.models.EmiDetailsOutput

internal class CalculateInterest() {
    suspend operator fun invoke(emiDetailsInput: EmiDetailsInput): EmiDetailsOutput {
        return EmiDetailsOutput(
            selected = emiDetailsInput.selected,
            principal = emiDetailsInput.principal.toString(),
            interest = calculateInterestRate(
                principal = emiDetailsInput.principal ?: 0,
                tenure = emiDetailsInput.tenure ?: 0,
                emi = emiDetailsInput.emi ?: 0
            ).toString(),
            tenure = emiDetailsInput.tenure.toString(),
            emi = emiDetailsInput.emi.toString()
        )
    }
}
