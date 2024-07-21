package com.allutils.feature_emi.domain.usecase

import com.allutils.feature_emi.domain.calculateAmortizationSchedule
import com.allutils.feature_emi.domain.calculateEMI
import com.allutils.feature_emi.domain.calculateTotalComponents
import com.allutils.feature_emi.domain.models.EmiDetailsInput
import com.allutils.feature_emi.domain.models.EmiDetailsOutput
import java.text.DecimalFormat

internal class CalculateEmi() {
    suspend operator fun invoke(emiDetailsInput: EmiDetailsInput): EmiDetailsOutput {
        val decimalFormat = DecimalFormat("#.00")

        val amortizationSchedule = calculateAmortizationSchedule(
            emiDetailsInput.principal ?: 0,
            emiDetailsInput.interest ?: 0.0,
            emiDetailsInput.tenure ?: 0
        )
        amortizationSchedule.forEachIndexed { index, (principalComponent, interestComponent) ->
            println("Month ${index + 1}: Principal Component: $principalComponent, Interest Component: $interestComponent")
        }

        val (totalPrincipalComponent, totalInterestComponent) = calculateTotalComponents(amortizationSchedule)

        return EmiDetailsOutput(
            selected = emiDetailsInput.selected,
            principal = emiDetailsInput.principal.toString(),
            interest = decimalFormat.format(emiDetailsInput.interest),
            tenure = emiDetailsInput.tenure.toString(),
            emi = calculateEMI(
                principal = emiDetailsInput.principal ?: 0,
                tenure = emiDetailsInput.tenure ?: 0,
                rate = emiDetailsInput.interest ?: 0.0
            ).toString(),
            principalComponent = totalPrincipalComponent.toString(),
            interestComponent = totalInterestComponent.toString(),
            totalComponent = (totalPrincipalComponent + totalInterestComponent).toString()
        )
    }
}
