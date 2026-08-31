package com.appmachine.hahmisto.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.appmachine.hahmisto.ui.character.details.CharacterDetailsScreen
import com.appmachine.hahmisto.ui.character.form.CharacterFormScreen
import com.appmachine.hahmisto.ui.character.list.CharacterListScreen

@Composable
fun TasksNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Characters,
    ) {
        composable<Characters> {
            CharacterListScreen(
                onCreateCharacter = {
                    navController.navigate(CharacterForm(characterId = null))
                },
                onCharacterClick = { characterId ->
                    navController.navigate(
                        CharacterDetailsRoute(characterId = characterId)
                    )
                }
            )
        }

        composable<CharacterDetailsRoute> {
            CharacterDetailsScreen(
                navigateBack = { navController.navigateUp() }
            )
        }

        composable<CharacterForm> {
            CharacterFormScreen(
                onCancel = {
                    navController.popBackStack()
                },
                onSaved = {
                    navController.popBackStack()
                }
            )
        }
    }
}