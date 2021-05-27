package com.victormartin.ownpractice.data.remote.api

import com.victormartin.ownpractice.model.api.MarvelDataResponse
import retrofit2.Response
import retrofit2.http.GET

interface NetworkService {

    @GET("characters")
    suspend fun getMarvelHeroesList(): Response<MarvelDataResponse>

    @GET("characters/{id}")
    suspend fun getMarvelHeroeDetail(id: String): Response<MarvelDataResponse>

}