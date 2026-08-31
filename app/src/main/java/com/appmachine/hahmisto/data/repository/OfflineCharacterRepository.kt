package com.appmachine.hahmisto.data.repository

import com.appmachine.hahmisto.data.local.character.BackgroundDao
import com.appmachine.hahmisto.data.local.character.CharacterClassDao
import com.appmachine.hahmisto.data.local.character.CharacterDao
import com.appmachine.hahmisto.data.local.character.CharacterEntity
import com.appmachine.hahmisto.data.local.character.CharacterWithDetails
import com.appmachine.hahmisto.data.local.character.RaceDao
import com.appmachine.hahmisto.data.local.character.toNewEntity
import com.appmachine.hahmisto.data.local.character.toUpdatedEntity
import com.appmachine.hahmisto.domain.model.Background
import com.appmachine.hahmisto.domain.model.CharacterClass
import com.appmachine.hahmisto.domain.model.CharacterDraft
import com.appmachine.hahmisto.domain.model.PlayerCharacter
import com.appmachine.hahmisto.domain.model.Race
import com.appmachine.hahmisto.domain.model.toBackground
import com.appmachine.hahmisto.domain.model.toCharacter
import com.appmachine.hahmisto.domain.model.toCharacterClass
import com.appmachine.hahmisto.domain.model.toRace
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map



class OfflineCharacterRepository(
    private val characterDao: CharacterDao,
    private val raceDao: RaceDao,
    private val characterClassDao: CharacterClassDao,
    private val backgroundDao: BackgroundDao,
) : CharacterRepository {
    override fun getAllCharacters(): Flow<List<PlayerCharacter>> = characterDao
        .getAllCharactersWithDetails()
        .map { characters ->
            characters.map {
                it.toCharacter()
            }
        }

    override fun getAllRaces(): Flow<List<Race>> = raceDao.getAllRaces().map { races ->
        races.map {
            it.toRace()
        }
    }

    override fun getAllClasses(): Flow<List<CharacterClass>> =
        characterClassDao.getAllClasses()
            .map { classes ->
                classes.map {
                    it.toCharacterClass()
                }
            }

    override fun getAllBackgrounds(): Flow<List<Background>> =
        backgroundDao.getAllBackgrounds()
            .map { backgrounds ->
                backgrounds.map {
                    it.toBackground()
                }
            }

    override fun getCharacter(id: Long): Flow<PlayerCharacter?> =
        characterDao
            .getCharacterWithDetails(id)
            .map { characterWithDetails ->
                characterWithDetails?.toCharacter()
            }


    override suspend fun getCharacterOnce(id: Long): CharacterEntity? =
        characterDao.getCharacterOnce(id)

    override suspend fun createCharacter(character: CharacterDraft) {
        val now = System.currentTimeMillis()

        characterDao.insert(character.toNewEntity(now))
    }

    override suspend fun updateCharacter(characterId: Long, draft: CharacterDraft) {
        val existing = characterDao.getCharacterOnce(characterId)
            ?: throw IllegalArgumentException("Character not found")

        val now = System.currentTimeMillis()

        characterDao.update(
            draft.toUpdatedEntity(
                existing = existing,
                now = now,
            )
        )
    }

    override suspend fun deleteCharacter(character: CharacterEntity) =
        characterDao.delete(character)
}