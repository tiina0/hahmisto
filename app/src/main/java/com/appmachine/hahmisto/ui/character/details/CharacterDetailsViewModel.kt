package com.appmachine.hahmisto.ui.character.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.appmachine.hahmisto.data.repository.CharacterRepository
import com.appmachine.hahmisto.domain.model.PlayerCharacter
import com.appmachine.hahmisto.ui.character.list.CharacterListUiState
import com.appmachine.hahmisto.ui.navigation.CharacterDetailsRoute
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class CharacterDetailsUiState(
    val character: PlayerCharacter? = null,
    val isLoading: Boolean = true
)

class CharacterDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    characterRepository: CharacterRepository
) : ViewModel() {
    private val characterId: Long = savedStateHandle.toRoute<CharacterDetailsRoute>().characterId

    val uiState: StateFlow<CharacterDetailsUiState> =
        characterRepository.getCharacter(characterId)
            .filterNotNull()
            .map { character ->
                CharacterDetailsUiState(
                    character = character,
                    isLoading = false,
                    )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = CharacterDetailsUiState()
            )
}