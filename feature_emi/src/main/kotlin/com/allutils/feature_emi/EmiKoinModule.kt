package com.allutils.feature_emi

import com.allutils.feature_emi.di.dataModule
import com.allutils.feature_emi.di.domainModule
import com.allutils.feature_emi.di.presentationModule

val emiModules = listOf(
    presentationModule,
    domainModule,
    dataModule,
)
