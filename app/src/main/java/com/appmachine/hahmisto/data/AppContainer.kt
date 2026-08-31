package com.appmachine.hahmisto.data

import android.content.Context
import com.appmachine.hahmisto.data.local.AppDatabase
import com.appmachine.hahmisto.data.repository.OfflineCharacterRepository
import com.appmachine.hahmisto.data.settings.SettingsRepository

interface AppContainer {
    val settingsRepository: SettingsRepository
    val characterRepository: OfflineCharacterRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val settingsRepository: SettingsRepository = SettingsRepository(context)
    override val characterRepository: OfflineCharacterRepository by lazy {
        val db = AppDatabase.getDatabase(context);
        OfflineCharacterRepository(
            db.characterDao(),
            db.raceDao(),
            db.characterClassDao(),
            db.backgroundDao(),
        )
    }

}

