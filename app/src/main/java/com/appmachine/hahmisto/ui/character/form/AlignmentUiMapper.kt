package com.appmachine.hahmisto.ui.character.form

import com.appmachine.hahmisto.domain.model.CharacterAlignment

fun CharacterAlignment.toDisplayName(): String =
    when (this) {
        CharacterAlignment.LAWFUL_GOOD -> "Lawful Good"
        CharacterAlignment.NEUTRAL_GOOD -> "Neutral Good"
        CharacterAlignment.CHAOTIC_GOOD -> "Chaotic Good"
        CharacterAlignment.LAWFUL_NEUTRAL -> "Lawful Neutral"
        CharacterAlignment.TRUE_NEUTRAL -> "True Neutral"
        CharacterAlignment.CHAOTIC_NEUTRAL -> "Chaotic Neutral"
        CharacterAlignment.LAWFUL_EVIL -> "Lawful Evil"
        CharacterAlignment.NEUTRAL_EVIL -> "Neutral Evil"
        CharacterAlignment.CHAOTIC_EVIL -> "Chaotic Evil"
    }