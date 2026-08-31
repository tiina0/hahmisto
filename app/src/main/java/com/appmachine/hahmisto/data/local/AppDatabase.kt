package com.appmachine.hahmisto.data.local

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import com.appmachine.hahmisto.data.local.character.BackgroundDao
import com.appmachine.hahmisto.data.local.character.BackgroundEntity
import com.appmachine.hahmisto.data.local.character.CharacterClassDao
import com.appmachine.hahmisto.data.local.character.CharacterClassEntity
import com.appmachine.hahmisto.data.local.character.CharacterDao
import com.appmachine.hahmisto.data.local.character.CharacterEntity
import com.appmachine.hahmisto.data.local.character.RaceDao
import com.appmachine.hahmisto.data.local.character.RaceEntity

@Database(
    entities = [
        CharacterEntity::class,
        CharacterClassEntity::class,
        RaceEntity::class,
        BackgroundEntity::class
    ],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun backgroundDao(): BackgroundDao
    abstract fun characterClassDao(): CharacterClassDao
    abstract fun raceDao(): RaceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database",
                )
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}