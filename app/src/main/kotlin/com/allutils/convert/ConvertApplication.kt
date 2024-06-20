package com.allutils.convert

import android.app.Application
import android.os.StrictMode
import com.allutils.base.baseModule
import com.allutils.convert.BuildConfig.DEBUG
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import timber.log.Timber

class ConvertApplication : Application() {

    override fun onCreate() {
        enableStrictMode()
        super.onCreate()

        initKoin()
        initTimber()
        createAppShortCuts()
    }

    private fun initKoin() {
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@ConvertApplication)

            modules(appModule)
            modules(baseModule)
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

    private fun createAppShortCuts() {
        val shortcutManager = ShortcutsHandler(this)
        shortcutManager.createShortcuts()
    }
}
