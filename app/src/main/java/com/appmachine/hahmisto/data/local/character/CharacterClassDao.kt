package com.appmachine.hahmisto.data.local.character

import androidx.room3.Dao
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterClassDao {
    @Query("SELECT * FROM character_classes ORDER BY name ASC")
    fun getAllClasses(): Flow<List<CharacterClassEntity>>

    @Query("SELECT * FROM character_classes WHERE id = :id LIMIT 1")
    fun getClass(id: Long): Flow<CharacterClassEntity>?

    @Query("SELECT * FROM character_classes WHERE id = :id LIMIT 1")
    fun getClassOnce(id: Long): CharacterClassEntity?

    @Query("SELECT * FROM character_classes WHERE stableKey = :stableKey LIMIT 1")
    fun getClassByKey(stableKey: String): CharacterClassEntity?
}