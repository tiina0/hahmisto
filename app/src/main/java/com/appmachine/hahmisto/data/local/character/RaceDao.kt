package com.appmachine.hahmisto.data.local.character

import androidx.room3.Dao
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RaceDao {
    @Query("SELECT * FROM races ORDER BY name ASC")
    fun getAllRaces(): Flow<List<RaceEntity>>

    @Query("SELECT * FROM races WHERE id = :id LIMIT 1")
    fun getRace(id: Long): Flow<RaceEntity?>

    @Query("SELECT * FROM races WHERE id = :id LIMIT 1")
    fun getRaceOnce(id: Long): RaceEntity?

    @Query("SELECT * FROM races WHERE stableKey = :stableKey LIMIT 1")
    fun getRaceByKey(stableKey: String): RaceEntity?
}