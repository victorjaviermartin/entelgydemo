package com.victormartin.ownpractice.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.victormartin.ownpractice.model.dao.MarvelHeroEntityModel
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for [com.victormartin.ownpractice.data.local.AppDatabase]
 */
@Dao
interface MarvelHeroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMarvelHeroes(heroesList: List<MarvelHeroEntityModel>)

    @Query("DELETE FROM ${MarvelHeroEntityModel.TABLE_NAME}")
    fun deleteAllHeroes()

    @Query("SELECT * FROM ${MarvelHeroEntityModel.TABLE_NAME}")
    fun getAllHeroes(): Flow<List<MarvelHeroEntityModel>>

    @Query("SELECT * FROM ${MarvelHeroEntityModel.TABLE_NAME} WHERE name = :inputName")
    fun getHeroByName(inputName: String): Flow<MarvelHeroEntityModel>
}