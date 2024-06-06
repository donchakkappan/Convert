package com.allutils.feature_documents

import com.allutils.feature_documents.di.dataModule
import com.allutils.feature_documents.di.domainModule
import com.allutils.feature_documents.di.presentationModule

val documentsModules = listOf(
    presentationModule,
    domainModule,
    dataModule,
)
