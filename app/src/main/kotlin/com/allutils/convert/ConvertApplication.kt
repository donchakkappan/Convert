package com.allutils.convert

import android.app.Application
import android.os.StrictMode
import com.allutils.base.baseModule
import com.allutils.convert.BuildConfig.DEBUG
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
        enableStrictMode()
        super.onCreate()
        val s="lint"
        initKoin()
        initTimber()
    }

    fun lint() { }

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
        if (DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun enableStrictMode() {
        if (DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .build()
            )
        }
    }
}
