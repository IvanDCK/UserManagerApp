package org.martarcas.usermanager

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.martarcas.usermanager.di.initKoin

class UserManagerApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@UserManagerApp)
        }
    }
}