package com.appmachine.hahmisto.domain.model

data class CharacterDraft(
    val name: String,
    val imageUri: Int?,
    val raceId: Long,
    val classId: Long,
    val backgroundId: Long,
    val level: Int,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
    val maxHitPoints: Int,
    val currentHitPoints: Int,
    val temporaryHitPoints: Int,
    val armorClass: Int,
    val alignment: CharacterAlignment,
    val notes: String,
)