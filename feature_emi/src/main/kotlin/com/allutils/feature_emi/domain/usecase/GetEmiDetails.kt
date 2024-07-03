package com.allutils.feature_emi.domain.usecase

import com.allutils.feature_emi.domain.models.EmiDetailsInput
import com.allutils.feature_emi.domain.models.EmiDetailsOutput

internal class GetEmiDetails() {
    suspend operator fun invoke(emiDetailsInput: EmiDetailsInput): EmiDetailsOutput {
        return EmiDetailsOutput(
            principal = (emiDetailsInput.principal * 2).toString(),
            interest = (emiDetailsInput.interest * 2).toString(),
            tenure = (emiDetailsInput.tenure * 2).toString(),
            emi = (emiDetailsInput.emi * 2).toString()
        )
    }
}
