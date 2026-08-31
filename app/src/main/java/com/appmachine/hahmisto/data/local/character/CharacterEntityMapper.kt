package com.appmachine.hahmisto.data.local.character

import com.appmachine.hahmisto.domain.model.CharacterDraft

fun CharacterDraft.toNewEntity(now: Long): CharacterEntity {
    return CharacterEntity(
        name = name,
        imageUri = imageUri,
        raceId = raceId,
        classId = classId,
        backgroundId = backgroundId,
        level = level,
        strength = strength,
        dexterity = dexterity,
        constitution = constitution,
        intelligence = intelligence,
        wisdom = wisdom,
        charisma = charisma,
        hitPointsMax = maxHitPoints,
        hitPointsCurrent = currentHitPoints,
        temporaryHitPoints = temporaryHitPoints,
        armorClass = armorClass,
        alignment = alignment.name,
        notes = notes,
        createdAt = now,
        updatedAt = now,
    )
}

fun CharacterDraft.toUpdatedEntity(
    existing: CharacterEntity,
    now: Long,
): CharacterEntity {
    return existing.copy(
        name = name,
        imageUri = imageUri,
        raceId = raceId,
        classId = classId,
        backgroundId = backgroundId,
        level = level,
        strength = strength,
        dexterity = dexterity,
        constitution = constitution,
        intelligence = intelligence,
        wisdom = wisdom,
        charisma = charisma,
        hitPointsMax = maxHitPoints,
        hitPointsCurrent = currentHitPoints,
        temporaryHitPoints = temporaryHitPoints,
        armorClass = armorClass,
        alignment = alignment.name,
        notes = notes,
        updatedAt = now,
    )
}