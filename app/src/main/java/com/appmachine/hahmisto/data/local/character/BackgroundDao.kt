package com.appmachine.hahmisto.data.local.character

import androidx.room3.Dao
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BackgroundDao {
    @Query("SELECT * FROM backgrounds ORDER BY name ASC")
    fun getAllBackgrounds(): Flow<List<BackgroundEntity>>

    @Query("SELECT * FROM backgrounds WHERE id = :id LIMIT 1")
    fun getBackground(id: Long): Flow<BackgroundEntity?>

    @Query("SELECT * FROM backgrounds WHERE id = :id LIMIT 1")
    fun getBackgroundOnce(id: Long): BackgroundEntity?

    @Query("SELECT * FROM backgrounds WHERE stableKey = :stableKey LIMIT 1")
    fun getBackgroundByKey(stableKey: String): BackgroundEntity?
}