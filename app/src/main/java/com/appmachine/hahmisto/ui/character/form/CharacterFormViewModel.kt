package com.appmachine.hahmisto.ui.character.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appmachine.hahmisto.data.repository.OfflineCharacterRepository
import com.appmachine.hahmisto.domain.model.Background
import com.appmachine.hahmisto.domain.model.CharacterAlignment
import com.appmachine.hahmisto.domain.model.CharacterClass
import com.appmachine.hahmisto.domain.model.Race
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterFormViewModel(private val characterRepository: OfflineCharacterRepository) :
    ViewModel() {
    private val characterDetails = MutableStateFlow(CharacterDetails())

    private val _events = MutableSharedFlow<CharacterFormEvent>()
    val events = _events.asSharedFlow()

    val characterUiState: StateFlow<CharacterUiState> =
        combine(
            characterDetails,
            characterRepository.getAllRaces(),
            characterRepository.getAllClasses(),
            characterRepository.getAllBackgrounds()
        ) { details, races, classes, backgrounds ->
            CharacterUiState(
                characterDetails = details,
                races = races,
                characterClasses = classes,
                backgrounds = backgrounds,
                isCharacterValid = isCharacterValid(details)
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CharacterUiState()
            )

    fun updateName(name: String) {
        characterDetails.update { currentDetails ->
            currentDetails.copy(name = name)
        }
    }

    fun updateRace(id: Long) {
        characterDetails.update { currentDetails ->
            currentDetails.copy(raceId = id)
        }
    }

    fun updateClass(id: Long) {
        characterDetails.update { currentDetails ->
            currentDetails.copy(classId = id)
        }
    }

    fun updateBackground(id: Long) {
        characterDetails.update { currentDetails ->
            currentDetails.copy(backgroundId = id)
        }
    }

    fun updateLevel(level: Int) {
        characterDetails.update { currentDetails ->
            currentDetails.copy(
                level = level.coerceIn(1, 20),
            )
        }
    }


    fun updateMaxHitPoints(maxHitPoints: Int?) {
        characterDetails.update { currentDetails ->
            currentDetails.copy(
                maxHitPoints = maxHitPoints,
                currentHitPoints = currentDetails.currentHitPoints ?: maxHitPoints,
            )

        }
    }

    fun updateCurrentHitPoints(currentHitPoints: Int?) {
        characterDetails.update { currentDetails ->
            currentDetails.copy(
                currentHitPoints = currentHitPoints,
            )
        }
    }

    fun updateArmorClass(armorClass: Int?) {
        characterDetails.update { currentDetails ->
            currentDetails.copy(
                armorClass = armorClass,
            )
        }
    }

    fun updateAlignment(alignment: CharacterAlignment) {
        characterDetails.update { currentDetails ->
            currentDetails.copy(
                alignment = alignment,
            )
        }
    }

    fun updateStrength(strength: Int) {
        characterDetails.update { currentDetails ->
            currentDetails.copy(
                strength = strength,
            )
        }
    }

    fun updateDexterity(dexterity: Int) {
        characterDetails.update { currentDetails ->
            currentDetails.copy(
                dexterity = dexterity,
            )
        }
    }

    fun updateConstitution(constitution: Int) {
        characterDetails.update { currentDetails ->
            currentDetails.copy(
                constitution = constitution,
            )
        }
    }

    fun updateIntelligence(intelligence: Int) {
        characterDetails.update { currentDetails ->
            currentDetails.copy(
                intelligence = intelligence,
            )
        }
    }

    fun updateWisdom(wisdom: Int) {
        characterDetails.update { currentDetails ->
            currentDetails.copy(
                wisdom = wisdom,
            )
        }
    }

    fun updateCharisma(charisma: Int) {
        characterDetails.update { currentDetails ->
            currentDetails.copy(
                charisma = charisma,
            )
        }
    }

    private fun isCharacterValid(details: CharacterDetails): Boolean {
        return details.name.isNotBlank() &&
                details.raceId != null &&
                details.classId != null &&
                details.backgroundId != null &&
                details.level in 1..20 &&
                details.maxHitPoints != null &&
                details.maxHitPoints > 0 &&
                details.currentHitPoints != null &&
                details.currentHitPoints in 0..details.maxHitPoints &&
                details.temporaryHitPoints >= 0 &&
                details.armorClass != null &&
                details.armorClass > 0 &&
                details.alignment != null &&
                details.strength in 1..20 &&
                details.dexterity in 1..20 &&
                details.constitution in 1..20 &&
                details.intelligence in 1..20 &&
                details.wisdom in 1..20 &&
                details.charisma in 1..20
    }


    fun saveCharacter(characterDetails: CharacterDetails) {
        if (!isCharacterValid(characterDetails)) return

        viewModelScope.launch {
            val draft = characterDetails.toCharacterDraft()

            characterRepository.createCharacter(draft)

            _events.emit(CharacterFormEvent.Saved)
        }
    }
}

data class CharacterUiState(
    val characterDetails: CharacterDetails = CharacterDetails(),
    val races: List<Race> = emptyList(),
    val characterClasses: List<CharacterClass> = emptyList(),
    val backgrounds: List<Background> = emptyList(),
    val isCharacterValid: Boolean = false,
)

data class CharacterDetails(
    val name: String = "",
    val imageUri: Int? = null,
    val raceId: Long? = null,
    val classId: Long? = null,
    val backgroundId: Long? = null,
    val level: Int = 1,
    val strength: Int = 10,
    val dexterity: Int = 10,
    val constitution: Int = 10,
    val intelligence: Int = 10,
    val wisdom: Int = 10,
    val charisma: Int = 10,
    val maxHitPoints: Int? = null,
    val currentHitPoints: Int? = null,
    val temporaryHitPoints: Int = 0,
    val armorClass: Int? = null,
    val alignment: CharacterAlignment? = null,
    val notes: String = "",
)