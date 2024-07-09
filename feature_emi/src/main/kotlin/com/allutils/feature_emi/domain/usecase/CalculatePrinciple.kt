package com.allutils.feature_emi.domain.usecase

import com.allutils.feature_emi.domain.calculateEMI
import com.allutils.feature_emi.domain.calculateInterestRate
import com.allutils.feature_emi.domain.calculatePrincipal
import com.allutils.feature_emi.domain.calculateTenure
import com.allutils.feature_emi.domain.models.EmiDetailsInput
import com.allutils.feature_emi.domain.models.EmiDetailsOutput

internal class CalculatePrinciple() {
    suspend operator fun invoke(emiDetailsInput: EmiDetailsInput): EmiDetailsOutput {
        if (emiDetailsInput.principal == null) { //find Principle
            return EmiDetailsOutput(
                principal = calculatePrincipal(
                    emi = emiDetailsInput.emi ?: 0,
                    tenure = emiDetailsInput.tenure ?: 0,
                    rate = emiDetailsInput.interest ?: 0.0
                ).toString(),
                interest = emiDetailsInput.interest.toString(),
                tenure = emiDetailsInput.tenure.toString(),
                emi = emiDetailsInput.emi.toString()
            )
        } else if (emiDetailsInput.interest == null) { //find Interest
            return EmiDetailsOutput(
                principal = emiDetailsInput.principal.toString(),
                interest = calculateInterestRate(
                    principal = emiDetailsInput.principal,
                    tenure = emiDetailsInput.tenure ?: 0,
                    emi = emiDetailsInput.emi ?: 0
                ).toString(),
                tenure = emiDetailsInput.tenure.toString(),
                emi = emiDetailsInput.emi.toString()
            )
        } else if (emiDetailsInput.emi == null) { //find EMI
            return EmiDetailsOutput(
                principal = emiDetailsInput.principal.toString(),
                interest = emiDetailsInput.interest.toString(),
                tenure = emiDetailsInput.tenure.toString(),
                emi = calculateEMI(
                    principal = emiDetailsInput.principal,
                    tenure = emiDetailsInput.tenure?.toInt()?:0,
                    rate = emiDetailsInput.interest
                ).toString()
            )
        } else { //find Tenure
            return EmiDetailsOutput(
                principal = emiDetailsInput.principal.toString(),
                interest = emiDetailsInput.interest.toString(),
                tenure = calculateTenure(
                    principal = emiDetailsInput.principal,
                    rate = emiDetailsInput.interest,
                    emi = emiDetailsInput.emi
                ).toString(),
                emi = emiDetailsInput.emi.toString(),
            )
        }
    }
}
