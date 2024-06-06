package com.allutils.feature_currency

import com.allutils.feature_currency.di.dataModule
import com.allutils.feature_currency.di.domainModule
import com.allutils.feature_currency.di.presentationModule

val currencyModules = listOf(
    presentationModule,
    domainModule,
    dataModule,
)
