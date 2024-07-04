package com.allutils.feature_emi.domain.models

data class EmiDetailsInput(
    val principal: Double? = null,
    val interest: Double? = null,
    val tenure: Double? = null,
    val emi: Double? = null
)
