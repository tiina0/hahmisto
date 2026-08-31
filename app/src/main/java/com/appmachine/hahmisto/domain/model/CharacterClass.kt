package com.appmachine.hahmisto.domain.model

import com.appmachine.hahmisto.data.local.character.CharacterClassEntity

data class CharacterClass(
    val id: Long,
    val name: String,
    val key: String,
    val description: String? = null,
    val source: String? = null,
    val isCustom: Boolean = false,
)

fun CharacterClassEntity.toCharacterClass(): CharacterClass = CharacterClass(
    id = id,
    name = name,
    key = stableKey,
    description = description,
    source = source,
    isCustom = isCustom,
)