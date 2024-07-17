package com.allutils.feature_emi.domain.usecase

import com.allutils.feature_emi.domain.calculateInterestRate
import com.allutils.feature_emi.domain.models.EmiDetailsInput
import com.allutils.feature_emi.domain.models.EmiDetailsOutput
import java.text.DecimalFormat

internal class CalculateInterest() {
    suspend operator fun invoke(emiDetailsInput: EmiDetailsInput): EmiDetailsOutput {
        val decimalFormat = DecimalFormat("#.00")

        return EmiDetailsOutput(
            selected = emiDetailsInput.selected,
            principal = emiDetailsInput.principal.toString(),
            interest = decimalFormat.format(
                calculateInterestRate(
                    principal = emiDetailsInput.principal ?: 0,
                    tenure = emiDetailsInput.tenure ?: 0,
                    emi = emiDetailsInput.emi ?: 0
                )
            ),
            tenure = emiDetailsInput.tenure.toString(),
            emi = emiDetailsInput.emi.toString()
        )
    }
}
