package com.allutils.feature_emi.domain.models

data class EmiDetailsOutput(
    val principal: String,
    val interest: String,
    val tenure: String,
    val emi: String
)
