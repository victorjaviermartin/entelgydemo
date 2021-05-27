package com.victormartin.ownpractice.model.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.victormartin.ownpractice.model.api.MarvelHeroThumbnail
import com.victormartin.ownpractice.model.dao.MarvelHeroEntityModel.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class MarvelHeroEntityModel(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "path") val path: String = "",
    @ColumnInfo(name = "extension") val extension: String = "",
    @ColumnInfo(name = "photoUrl") val photoUrl: String = path + extension
) {
    companion object {
        const val TABLE_NAME = "table_heroes"
    }

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}
