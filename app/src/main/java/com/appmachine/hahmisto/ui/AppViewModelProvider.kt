package com.appmachine.hahmisto.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.appmachine.hahmisto.HahmistoApplication
import com.appmachine.hahmisto.ui.character.details.CharacterDetailsViewModel
import com.appmachine.hahmisto.ui.character.form.CharacterFormViewModel
import com.appmachine.hahmisto.ui.character.list.CharacterListViewModel
import com.appmachine.hahmisto.ui.settings.SettingsViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            SettingsViewModel(hahmistoApp().container.settingsRepository)
        }
        initializer {
            CharacterListViewModel(hahmistoApp().container.characterRepository)
        }
        initializer {
            CharacterFormViewModel(hahmistoApp().container.characterRepository)
        }
        initializer {
            CharacterDetailsViewModel(
                this.createSavedStateHandle(),
                hahmistoApp().container.characterRepository
            )
        }
    }
}

fun CreationExtras.hahmistoApp(): HahmistoApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as HahmistoApplication)