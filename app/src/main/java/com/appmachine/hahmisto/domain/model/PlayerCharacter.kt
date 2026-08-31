package com.appmachine.hahmisto.domain.model

import com.appmachine.hahmisto.data.local.character.CharacterWithDetails

data class PlayerCharacter(
    val id: Long,
    val name: String,
    val race: Race,
    val characterClass: CharacterClass,
    val background: Background,
    val level: Int,
    val hitPointsMax: Int,
    val hitPointsCurrent: Int,
    val armorClass: Int,
    val notes: String,
    val alignment: CharacterAlignment,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
)

fun CharacterWithDetails.toCharacter() = PlayerCharacter(
    id = character.id,
    name = character.name,
    race = race.toRace(),
    characterClass = characterClass.toCharacterClass(),
    background = background.toBackground(),
    level = character.level,
    hitPointsMax = character.hitPointsMax,
    hitPointsCurrent = character.hitPointsCurrent,
    armorClass = character.armorClass,
    notes = character.notes,
    alignment = CharacterAlignment.valueOf(character.alignment),
    strength = character.strength,
    dexterity = character.dexterity,
    constitution = character.constitution,
    intelligence = character.intelligence,
    wisdom = character.wisdom,
    charisma = character.charisma,
)
