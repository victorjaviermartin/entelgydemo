package com.victormartin.ownpractice.data.repository

import androidx.annotation.MainThread
import com.victormartin.ownpractice.data.local.dao.MarvelHeroDao
import com.victormartin.ownpractice.data.remote.api.NetworkService
import com.victormartin.ownpractice.model.api.MarvelDataResponse
import com.victormartin.ownpractice.model.api.mapper.transformList
import com.victormartin.ownpractice.model.dao.MarvelHeroEntityModel
import com.victormartin.ownpractice.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

/**
 * Singleton repository for fetching data from remote and storing it in database
 * for offline capability. This is Single source of data.
 */

class DataRepository constructor(
    private val marvelHeroDao: MarvelHeroDao,
    private val networkService: NetworkService
) {

    /**
     * Fetched data from network and stored it in database. At the end, data from persistence
     * storage is fetched and emitted.
     */
    fun getAllHeroes(): Flow<State<List<MarvelHeroEntityModel>>> {

        return object : NetworkBoundRepository<List<MarvelHeroEntityModel>, MarvelDataResponse>() {

            override suspend fun saveRemoteData(response: MarvelDataResponse) {
                marvelHeroDao.insertMarvelHeroes(transformList(response.heroesData.superheroes))
            }

            override fun fetchFromLocal(): Flow<List<MarvelHeroEntityModel>> = marvelHeroDao.getAllHeroes()

            override suspend fun fetchFromRemote(): Response<MarvelDataResponse> = networkService.getMarvelHeroesList()

        }.asFlow().flowOn(Dispatchers.IO)
    }

    @MainThread
    fun getHeroByName(name: String): Flow<MarvelHeroEntityModel> = marvelHeroDao.getHeroByName(name)
}
