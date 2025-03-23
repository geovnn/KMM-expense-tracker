package com.giovannigatti.hackaton

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent

class MainApplication: Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
//        initKoin {
//            androidLogger()
//            androidContext(this@MainApplication)
//            modules(platformModule())
//        }

    }

}

//class MainApplication : Application() {
//    override fun onCreate() {
//        super.onCreate()
//        initKoin {
//            androidLogger()
//            androidContext(this@MainApplication)
//        }
//    }
//}