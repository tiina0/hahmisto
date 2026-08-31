package com.appmachine.hahmisto.ui.character.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appmachine.hahmisto.data.repository.OfflineCharacterRepository
import com.appmachine.hahmisto.domain.model.PlayerCharacter
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class CharacterListUiState(
    val characters: List<PlayerCharacter> = emptyList()
)


class CharacterListViewModel(
    private val characterRepository: OfflineCharacterRepository
) : ViewModel() {
    val uiState: StateFlow<CharacterListUiState> = characterRepository
        .getAllCharacters()
        .map { characters ->
            CharacterListUiState(
                characters = characters
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CharacterListUiState()
        )
}