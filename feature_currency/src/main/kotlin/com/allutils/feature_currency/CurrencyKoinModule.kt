package com.allutils.feature_currency

import com.allutils.feature_currency.di.dataModule
import com.allutils.feature_currency.di.domainModule
import com.allutils.feature_currency.di.mockDataModule
import com.allutils.feature_currency.di.presentationModule

val currencyModules = listOf(
    presentationModule,
    domainModule,
    if (BuildConfig.DEBUG) mockDataModule else dataModule,
)
