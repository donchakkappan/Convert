package com.allutils.feature_emi.domain.models

data class EmiDetailsOutput(
    val selected: String,
    val principal: String,
    val interest: String,
    val tenure: String,
    val emi: String
)
