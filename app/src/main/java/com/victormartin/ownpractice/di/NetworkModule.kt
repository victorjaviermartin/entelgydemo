package com.victormartin.ownpractice.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.victormartin.ownpractice.BuildConfig
import com.victormartin.ownpractice.data.remote.api.MarvelNetworkInterceptor
import com.victormartin.ownpractice.data.remote.api.NetworkService
import com.victormartin.ownpractice.data.repository.DataRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {

    single {
        val client =
            OkHttpClient.Builder()
                .addInterceptor(MarvelNetworkInterceptor())
                .build()

        Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
            .create(NetworkService::class.java)
    }

    single { DataRepository(get(), get()) }
}