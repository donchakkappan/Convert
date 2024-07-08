package com.allutils.feature_emi.domain

import kotlin.math.pow

//fun calculateEMI(principal: Int, rate: Double, tenure: Int): Double {
//    val monthlyRate = rate / (12 * 100) // Convert annual rate to monthly and percentage to decimal
//    val numerator = principal * monthlyRate * (1 + monthlyRate).pow(tenure)
//    val denominator = (1 + monthlyRate).pow(tenure) - 1
//    return numerator / denominator
//}

fun calculateEMI(principal: Int, rate: Double, tenure: Int): Int {
    val monthlyRate = rate / (12 * 100) // Convert annual rate to monthly and percentage to decimal
    val numerator = principal * monthlyRate * (1 + monthlyRate).pow(tenure)
    val denominator = (1 + monthlyRate).pow(tenure) - 1
    val emi = numerator / denominator
    return emi.toInt()
}

fun calculatePrincipal(emi: Int, rate: Double, tenure: Int): Int {
    val monthlyRate = rate / (12 * 100) // Convert annual rate to monthly and percentage to decimal
    val numerator = emi * ((1 + monthlyRate).pow(tenure) - 1)
    val denominator = monthlyRate * (1 + monthlyRate).pow(tenure)
    val principle = numerator / denominator
    return principle.toInt()
}

fun calculateTenure(principal: Int, emi: Int, rate: Double): Int {
    val monthlyRate = rate / (12 * 100) // Convert annual rate to monthly and percentage to decimal
    val tenure = (Math.log(emi / (emi - principal * monthlyRate)) / Math.log(1 + monthlyRate)).toInt()
    return tenure
}

fun calculateInterestRate(principal: Int, emi: Int, tenure: Int): Double {
    var rate = 0.0
    var high = 100.0
    var low = 0.0
    while (high - low > 0.0001) {
        rate = (high + low) / 2
        val calculatedEMI = calculateEMI(principal, rate, tenure)
        if (calculatedEMI > emi) {
            high = rate
        } else {
            low = rate
        }
    }
    return rate
}

fun calculateAmortizationSchedule(principal: Int, rate: Double, tenure: Int): List<Pair<Double, Double>> {
    val emi = calculateEMI(principal, rate, tenure)
    val monthlyRate = rate / (12 * 100)
    var remainingPrincipal = principal
    val scheduleList = mutableListOf<Pair<Double, Double>>()

    for (month in 1..tenure) {
        val interestComponent = remainingPrincipal * monthlyRate
        val principalComponent = emi - interestComponent
        //remainingPrincipal -= principalComponent.
        //scheduleList.add(Pair(principalComponent, interestComponent))
    }

    return scheduleList
}

fun calculateTotalComponents(schedule: List<Pair<Double, Double>>): Pair<Double, Double> {
    var totalPrincipalComponent = 0.0
    var totalInterestComponent = 0.0

    schedule.forEach { (principalComponent, interestComponent) ->
        totalPrincipalComponent += principalComponent
        totalInterestComponent += interestComponent
    }

    return Pair(totalPrincipalComponent, totalInterestComponent)
}

fun main() {
    val principal = 100000
    val rate = 10.0 // annual interest rate
    val tenure = 12 // tenure in months

    val emi = calculateEMI(principal, rate, tenure)
    println("EMI: $emi")

    val calculatedPrincipal = calculatePrincipal(emi, rate, tenure)
    println("Principal: $calculatedPrincipal")

    val calculatedTenure = calculateTenure(principal, emi, rate)
    println("Tenure: $calculatedTenure")

    val calculatedRate = calculateInterestRate(principal, emi, tenure)
    println("Interest Rate: $calculatedRate")

    val amortizationSchedule = calculateAmortizationSchedule(principal, rate, tenure.toInt())
    amortizationSchedule.forEachIndexed { index, (principalComponent, interestComponent) ->
        println("Month ${index + 1}: Principal Component: $principalComponent, Interest Component: $interestComponent")
    }

    val (totalPrincipalComponent, totalInterestComponent) = calculateTotalComponents(amortizationSchedule)
    println("Total Principal Component: $totalPrincipalComponent")
    println("Total Interest Component: $totalInterestComponent")

    val principalPercentage = (totalPrincipalComponent / (totalPrincipalComponent + totalInterestComponent)) * 100
    val interestPercentage = (totalInterestComponent / (totalPrincipalComponent + totalInterestComponent)) * 100

    println("Principal Component Percentage: $principalPercentage%")
    println("Interest Component Percentage: $interestPercentage%")
}
