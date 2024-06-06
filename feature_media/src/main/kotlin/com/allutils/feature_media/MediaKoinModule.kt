package com.allutils.feature_media

import com.allutils.feature_media.di.dataModule
import com.allutils.feature_media.di.domainModule
import com.allutils.feature_media.di.presentationModule

val mediaModules = listOf(
    presentationModule,
    domainModule,
    dataModule,
)
