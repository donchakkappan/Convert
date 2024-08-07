package com.allutils.feature_emi.domain.usecase

import com.allutils.feature_emi.domain.calculateTenure
import com.allutils.feature_emi.domain.models.EmiDetailsInput
import com.allutils.feature_emi.domain.models.EmiDetailsOutput
import java.text.DecimalFormat

internal class CalculateTenure() {
    suspend operator fun invoke(emiDetailsInput: EmiDetailsInput): EmiDetailsOutput {
        val decimalFormat = DecimalFormat("#.00")

        return EmiDetailsOutput(
            selected = emiDetailsInput.selected,
            principal = emiDetailsInput.principal.toString(),
            interest = decimalFormat.format(emiDetailsInput.interest),
            tenure = calculateTenure(
                principal = emiDetailsInput.principal ?: 0,
                rate = emiDetailsInput.interest ?: 0.0,
                emi = emiDetailsInput.emi ?: 0
            ).toString(),
            emi = emiDetailsInput.emi.toString(),
        )
    }
}
