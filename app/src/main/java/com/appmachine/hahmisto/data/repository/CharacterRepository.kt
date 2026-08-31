package com.appmachine.hahmisto.data.repository

import com.appmachine.hahmisto.data.local.character.CharacterEntity
import com.appmachine.hahmisto.domain.model.Background
import com.appmachine.hahmisto.domain.model.CharacterClass
import com.appmachine.hahmisto.domain.model.CharacterDraft
import com.appmachine.hahmisto.domain.model.PlayerCharacter
import com.appmachine.hahmisto.domain.model.Race
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getAllCharacters(): Flow<List<PlayerCharacter>>
    fun getCharacter(id: Long): Flow<PlayerCharacter?>
    suspend fun getCharacterOnce(id: Long): CharacterEntity?
    fun getAllRaces(): Flow<List<Race>>
    fun getAllClasses(): Flow<List<CharacterClass>>
    fun getAllBackgrounds(): Flow<List<Background>>
    suspend fun createCharacter(character: CharacterDraft)
    suspend fun updateCharacter(characterId: Long, draft: CharacterDraft)
    suspend fun deleteCharacter(character: CharacterEntity)
}