package com.allutils.convert

import android.app.Application
import com.allutils.base.BuildConfig
import com.allutils.base.baseModule
import com.allutils.feature_currency.currencyModules
import com.allutils.feature_documents.documentsModules
import com.allutils.feature_emi.emiModules
import com.allutils.feature_media.mediaModules
import com.allutils.feature_scanner.scannerModules
import com.allutils.feature_units.unitsModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import timber.log.Timber

class ConvertApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
        initTimber()
    }

    private fun initKoin() {
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@ConvertApplication)

            modules(appModule)
            modules(baseModule)
            modules(currencyModules)
            modules(documentsModules)
            modules(emiModules)
            modules(mediaModules)
            modules(scannerModules)
            modules(unitsModules)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}