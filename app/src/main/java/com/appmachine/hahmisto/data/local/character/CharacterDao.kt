package com.appmachine.hahmisto.data.local.character

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters ORDER BY name ASC")
    fun getAllCharacters(): Flow<List<CharacterEntity>>

    @Transaction
    @Query("SELECT * FROM characters ORDER BY name ASC")
    fun getAllCharactersWithDetails(): Flow<List<CharacterWithDetails>>

    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacter(id: Long): Flow<CharacterEntity?>

    @Transaction
    @Query("SELECT * FROM characters WHERE id = :id LIMIT 1")
    fun getCharacterWithDetails(id: Long): Flow<CharacterWithDetails?>

    @Query("SELECT * FROM characters WHERE id = :id LIMIT 1")
    suspend fun getCharacterOnce(id: Long): CharacterEntity?

    @Insert
    suspend fun insert(character: CharacterEntity): Long

    @Update
    suspend fun update(character: CharacterEntity)

    @Delete
    suspend fun delete(character: CharacterEntity)
}