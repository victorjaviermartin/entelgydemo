package com.victormartin.ownpractice

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.victormartin.ownpractice.di.databaseModule
import com.victormartin.ownpractice.di.networkModule
import com.victormartin.ownpractice.di.viewModelModule
import com.victormartin.ownpractice.utils.isNight
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class OwnPracticeApp : Application() {


    override fun onCreate() {
        super.onCreate()

        initKoin()
        // Get UI mode and set
        val mode = if (isNight()) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }

        AppCompatDelegate.setDefaultNightMode(mode)
    }

    private fun initKoin() {
        startKoin {
            androidContext(applicationContext)
            modules(networkModule, databaseModule, viewModelModule)
        }
    }
}