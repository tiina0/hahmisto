package com.appmachine.hahmisto.ui.character.list

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appmachine.hahmisto.MainTopBar
import com.appmachine.hahmisto.R
import com.appmachine.hahmisto.domain.model.PlayerCharacter
import com.appmachine.hahmisto.ui.AppViewModelProvider

@Composable
fun CharacterListScreen(
    modifier: Modifier = Modifier,
    characterListViewModel: CharacterListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onCreateCharacter: () -> Unit,
    onCharacterClick: (Long) -> Unit,
) {
    val uiState by characterListViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { MainTopBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateCharacter,
                modifier = Modifier
                    .size(80.dp),
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                )
                {
                    Icon(
                        painterResource(R.drawable.add_48px),
                        contentDescription = stringResource(R.string.create_new_character)
                    )
                }
            }
        }
    ) { innerPadding ->
        if (uiState.characters.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painterResource(R.drawable.empty_character_list),
                        contentDescription = null,
                        modifier = Modifier.padding(16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.no_characters_yet),
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
        } else {
            CharacterList(
                modifier = modifier,
                contentPadding = innerPadding,
                uiState.characters,
                onCharacterClick = { onCharacterClick(it) }
            )
        }
    }
}


@Composable
fun CharacterList(
    modifier: Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    characters: List<PlayerCharacter>,
    onCharacterClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        items(
            items = characters,
            key = { character -> character.id },
        ) { character ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable { onCharacterClick(character.id) }
            ) {
                Row(
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 12.dp,
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CharacterAvatar(
                        imageRes = null,
                    )
                    Spacer(Modifier.width(8.dp))
                    Column(
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier.weight(1f),
                    ) {
                        Row {
                            Text(
                                text = character.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            LevelChip(level = character.level)
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_human),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp),
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(character.race.name)
                            Spacer(Modifier.width(4.dp))
                            Text("|", style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray))
                            Spacer(Modifier.width(4.dp))
                            Icon(
                                painter = painterResource(R.drawable.shield),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.size(20.dp),
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(character.characterClass.name)
                        }
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun LevelChip(level: Int, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(30),
        color = MaterialTheme.colorScheme.secondaryContainer,
    ) {
        Text(
            text = stringResource(R.string.character_level, level),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }

}

@Composable
fun CharacterAvatar(
    modifier: Modifier = Modifier,
    size: Int = 72,
    @DrawableRes imageRes: Int?,
) {
    val painter = imageRes?.let { painterResource(it) }
        ?: painterResource(R.drawable.placeholder_avatar_1_grayscale)

    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
            )
    )
}