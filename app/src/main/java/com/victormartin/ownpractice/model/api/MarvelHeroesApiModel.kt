package com.victormartin.ownpractice.model.api

import com.squareup.moshi.Json

data class MarvelDataResponse(@Json(name = "data") val heroesData: MarvelHeroesResponse)
data class MarvelHeroesResponse(@Json(name = "results") val superheroes: List<MarvelHeroResponse>)
data class MarvelHeroResponse(
    val name: String = "",
    val thumbnail: MarvelHeroThumbnail = MarvelHeroThumbnail(),
    val description: String = ""
)

data class MarvelHeroThumbnail(val path: String = "", val extension: String = "") {
    val url = "$path.$extension"
}
