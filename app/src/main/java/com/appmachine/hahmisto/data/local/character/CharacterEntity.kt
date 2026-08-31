package com.appmachine.hahmisto.data.local.character

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey
import java.util.Date

@Entity(
    tableName = "characters",
    foreignKeys = [
        ForeignKey(
            entity = RaceEntity::class,
            parentColumns = ["id"],
            childColumns = ["raceId"],
            onDelete = ForeignKey.RESTRICT,
        ),
        ForeignKey(
            entity = CharacterClassEntity::class,
            parentColumns = ["id"],
            childColumns = ["classId"],
            onDelete = ForeignKey.RESTRICT,
        ),
        ForeignKey(
            entity = BackgroundEntity::class,
            parentColumns = ["id"],
            childColumns = ["backgroundId"],
            onDelete = ForeignKey.RESTRICT,
        )
    ],
    indices = [
        Index("raceId"),
        Index("classId"),
        Index("backgroundId"),
    ]
)
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,

    val imageUri: Int? = null,

    val raceId: Long,
    val classId: Long,
    val backgroundId: Long,

    val level: Int,

    val strength: Int = 10,
    val dexterity: Int = 10,
    val constitution: Int = 10,
    val intelligence: Int = 10,
    val wisdom: Int = 10,
    val charisma: Int = 10,

    val hitPointsMax: Int,
    val hitPointsCurrent: Int,
    val temporaryHitPoints: Int = 0,

    val armorClass: Int,

    val alignment: String,
    val notes: String = "",
    val createdAt: Long,
    val updatedAt: Long? = null,
)