package org.martarcas.usermanager

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.martarcas.usermanager.manager.di.initKoin

class UserManagerApp: Application() {
    override fun onCreate() {
        super.onCreate()
        if (!isRunningInTestMode()) {
            initKoin {
                androidContext(this@UserManagerApp)
            }
        }
    }

    fun isRunningInTestMode(): Boolean {
        return System.getProperty("isTestMode") == "true"
    }
}

