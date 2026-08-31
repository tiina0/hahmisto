package com.appmachine.hahmisto.domain.model

import androidx.annotation.StringRes
import com.appmachine.hahmisto.R

enum class CharacterAlignment {
    LAWFUL_GOOD,
    NEUTRAL_GOOD,
    CHAOTIC_GOOD,
    LAWFUL_NEUTRAL,
    TRUE_NEUTRAL,
    CHAOTIC_NEUTRAL,
    LAWFUL_EVIL,
    NEUTRAL_EVIL,
    CHAOTIC_EVIL,
}

@StringRes
fun CharacterAlignment.stringResourceId(): Int =
    when (this) {
        CharacterAlignment.LAWFUL_GOOD -> R.string.alignment_lawful_good
        CharacterAlignment.NEUTRAL_GOOD -> R.string.alignment_neutral_good
        CharacterAlignment.CHAOTIC_GOOD -> R.string.alignment_chaotic_good
        CharacterAlignment.LAWFUL_NEUTRAL -> R.string.alignment_lawful_neutral
        CharacterAlignment.TRUE_NEUTRAL -> R.string.alignment_true_neutral
        CharacterAlignment.CHAOTIC_NEUTRAL -> R.string.alignment_chaotic_neutral
        CharacterAlignment.LAWFUL_EVIL -> R.string.alignment_lawful_evil
        CharacterAlignment.NEUTRAL_EVIL -> R.string.alignment_neutral_evil
        CharacterAlignment.CHAOTIC_EVIL -> R.string.alignment_chaotic_evil
    }