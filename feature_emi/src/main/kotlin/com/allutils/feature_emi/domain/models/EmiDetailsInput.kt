package com.allutils.feature_emi.domain.models

data class EmiDetailsInput(
    val principal: Int? = null,
    val interest: Double? = null,
    val tenure: Int? = null,
    val emi: Int? = null
)
