package com.appmachine.hahmisto.data.local.character

import androidx.room3.Embedded
import androidx.room3.Relation

data class CharacterWithDetails(
    @Embedded
    val character: CharacterEntity,

    @Relation(
        parentColumns = ["raceId"],
        entityColumns = ["id"],
    )
    val race: RaceEntity,

    @Relation(
        parentColumns = ["classId"],
        entityColumns = ["id"],
    )
    val characterClass: CharacterClassEntity,

    @Relation(
        parentColumns = ["backgroundId"],
        entityColumns = ["id"],
    )

    val background: BackgroundEntity,
)
