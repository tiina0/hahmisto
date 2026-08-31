package com.appmachine.hahmisto.ui.character.form

import com.appmachine.hahmisto.domain.model.CharacterDraft

fun CharacterDetails.toCharacterDraft(): CharacterDraft {
    return CharacterDraft(
        name = name,
        imageUri = imageUri,
        raceId = requireNotNull(raceId),
        classId = requireNotNull(classId),
        backgroundId = requireNotNull(backgroundId),
        level = level,
        strength = strength,
        dexterity = dexterity,
        constitution = constitution,
        intelligence = intelligence,
        wisdom = wisdom,
        charisma = charisma,
        maxHitPoints = requireNotNull(maxHitPoints),
        currentHitPoints = requireNotNull(currentHitPoints),
        temporaryHitPoints = temporaryHitPoints,
        armorClass = requireNotNull(armorClass),
        alignment = requireNotNull(alignment),
        notes = notes,
    )
}