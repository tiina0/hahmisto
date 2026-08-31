package com.appmachine.hahmisto.data.local.character

import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "character_classes",
    indices = [
        Index(value = ["stableKey"], unique = true)
    ],
)
data class CharacterClassEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val stableKey: String,
    val name: String,
    val description: String? = null,
    val source: String? =null,
    val isCustom: Boolean = false,
)



