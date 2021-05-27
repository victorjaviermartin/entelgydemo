package com.victormartin.ownpractice.di

import com.victormartin.ownpractice.data.local.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single { AppDatabase.getInstance(androidApplication()) }
    single { get<AppDatabase>().getMarvelHeroDao() }
}