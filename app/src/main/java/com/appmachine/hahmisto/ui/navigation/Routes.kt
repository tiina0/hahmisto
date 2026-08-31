package com.appmachine.hahmisto.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object Characters

@Serializable
data class CharacterForm(val characterId: Long?)

@Serializable
data class CharacterDetailsRoute(val characterId: Long)