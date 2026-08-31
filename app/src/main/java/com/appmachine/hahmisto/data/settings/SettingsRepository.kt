package com.appmachine.hahmisto.data.settings

import android.content.Context
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class ThemeMode {
    LIGHT, DARK, SYSTEM
}

private const val PREFS_NAME = "settings"
private const val KEY_THEME_MODE = "theme_mode"

class SettingsRepository(context: Context) {
    private val preferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _themeMode = MutableStateFlow(readThemeMode())
    val themeModeState: StateFlow<ThemeMode> = _themeMode

    private fun readThemeMode(): ThemeMode {
        val setting = preferences.getString(KEY_THEME_MODE, ThemeMode.SYSTEM.name)
        return runCatching {
            ThemeMode.valueOf(setting ?: ThemeMode.SYSTEM.name)
        }.getOrDefault(ThemeMode.SYSTEM)
    }

    fun updateThemeMode(theme: ThemeMode) {
        preferences.edit { putString(KEY_THEME_MODE, theme.name) }
        _themeMode.value = theme
    }
}