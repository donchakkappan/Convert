package com.allutils.feature_emi.domain.models

data class EmiDetailsInput(
    val principal: Double,
    val interest: Double,
    val tenure: Double,
    val emi: Double
)
