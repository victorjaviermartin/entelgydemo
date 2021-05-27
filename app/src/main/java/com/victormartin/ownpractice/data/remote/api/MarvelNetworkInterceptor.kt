package com.victormartin.ownpractice.data.remote.api

import com.victormartin.ownpractice.BuildConfig
import com.victormartin.ownpractice.utils.md5
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

class MarvelNetworkInterceptor : Interceptor {

    companion object {
        const val API_KEY_NAME = "apikey"
        const val API_HASH_NAME = "hash"
        const val API_TIMESTAMP_NAME = "ts"
    }

    override fun intercept(chain: Chain): Response {
        val timestamp = getCurrentTimeStamp()
        val originalRequest = chain.request()

        val modifiedUrl = originalRequest.url()
            .newBuilder()
            .addQueryParameter(API_HASH_NAME, buildHash(timestamp))
            .addQueryParameter(API_KEY_NAME, BuildConfig.PUBLIC_KEY)
            .addQueryParameter(API_TIMESTAMP_NAME, timestamp.toString())
            .build()

        val newRequest = originalRequest.newBuilder().url(modifiedUrl).build()

        return chain.proceed(newRequest)
    }

    private fun buildHash(timestamp: Long) =
        (StringBuilder().append(timestamp).append(BuildConfig.PRIVATE_KEY)
            .append(BuildConfig.PUBLIC_KEY).toString()).md5()

    private fun getCurrentTimeStamp() = System.currentTimeMillis() / 1000L

}
