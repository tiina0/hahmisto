package com.appmachine.hahmisto.ui.character.form

sealed interface CharacterFormEvent {
    data object Saved : CharacterFormEvent
}