package com.victormartin.ownpractice.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.victormartin.ownpractice.data.local.dao.MarvelHeroDao
import com.victormartin.ownpractice.model.dao.MarvelHeroEntityModel

@Database(
    entities = [MarvelHeroEntityModel::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    /**
     * @return [MarvelHeroDao] Heroes Data Access Object.
     */
    abstract fun getMarvelHeroDao(): MarvelHeroDao

    companion object {
        const val DB_NAME = "app_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DB_NAME
                    )
                    .build()

                INSTANCE = instance
                return instance
            }
        }

    }
}