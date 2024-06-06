package com.allutils.feature_units

import com.allutils.feature_units.di.dataModule
import com.allutils.feature_units.di.domainModule
import com.allutils.feature_units.di.presentationModule

val unitsModules = listOf(
    presentationModule,
    domainModule,
    dataModule,
)
