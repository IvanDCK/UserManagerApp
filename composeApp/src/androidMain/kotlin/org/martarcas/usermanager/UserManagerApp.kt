package org.martarcas.usermanager

import android.app.Application
import com.martarcas.di.initKoin
import org.koin.android.ext.koin.androidContext

class UserManagerApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@UserManagerApp)
        }
    }
}