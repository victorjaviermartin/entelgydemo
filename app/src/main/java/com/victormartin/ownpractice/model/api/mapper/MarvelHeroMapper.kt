package com.victormartin.ownpractice.model.api.mapper

import com.victormartin.ownpractice.model.api.MarvelHeroResponse
import com.victormartin.ownpractice.model.dao.MarvelHeroEntityModel

private fun MarvelHeroResponse.toEntityType() = MarvelHeroEntityModel(
    name = name,
    photoUrl = thumbnail.path + "." + thumbnail.extension,
    description = description
)

fun transformList(inputList: List<MarvelHeroResponse>): List<MarvelHeroEntityModel> =
    inputList.map { it.toEntityType() }