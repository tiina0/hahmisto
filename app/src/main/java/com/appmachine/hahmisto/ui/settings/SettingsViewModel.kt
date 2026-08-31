package com.appmachine.hahmisto.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appmachine.hahmisto.data.settings.SettingsRepository
import com.appmachine.hahmisto.data.settings.ThemeMode
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class SettingsUIState(val themeMode: ThemeMode)

class SettingsViewModel(private val repository: SettingsRepository) : ViewModel() {
    val settingsUiState: StateFlow<SettingsUIState>
        get() = repository.themeModeState
            .map {
                SettingsUIState(it)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SettingsUIState(repository.themeModeState.value)
            )

    fun updateThemeMode(themeMode: ThemeMode) {
        repository.updateThemeMode(themeMode)
    }
}