package com.allutils.feature_scanner

import com.allutils.feature_scanner.di.dataModule
import com.allutils.feature_scanner.di.domainModule
import com.allutils.feature_scanner.di.presentationModule

val scannerModules = listOf(
    presentationModule,
    domainModule,
    dataModule,
)
