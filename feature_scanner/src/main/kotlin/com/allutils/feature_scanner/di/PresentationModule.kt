package com.allutils.feature_scanner.di

import com.allutils.feature_scanner.presentation.ScannerViewModel
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val presentationModule = module {

    single {
        GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .build()
    }

    single {
        GmsBarcodeScanning.getClient(get(), get())
    }

    viewModelOf(::ScannerViewModel)

}
