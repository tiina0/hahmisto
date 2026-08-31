package com.appmachine.hahmisto.domain.model

import com.appmachine.hahmisto.data.local.character.BackgroundEntity

data class Background(
    val id: Long,
    val name: String,
    val key: String,
    val description: String? = null,
    val source: String? = null,
    val isCustom: Boolean = false,
)

fun BackgroundEntity.toBackground(): Background = Background(
    id = id,
    name = name,
    key = stableKey,
    description = description,
    source = source,
    isCustom = isCustom,
)