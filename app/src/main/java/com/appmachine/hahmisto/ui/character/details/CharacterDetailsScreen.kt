package com.appmachine.hahmisto.ui.character.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Fireplace
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appmachine.hahmisto.R
import com.appmachine.hahmisto.domain.model.PlayerCharacter
import com.appmachine.hahmisto.domain.model.stringResourceId
import com.appmachine.hahmisto.ui.AppViewModelProvider
import com.appmachine.hahmisto.ui.character.list.CharacterAvatar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailsScreen(
    navigateBack: () -> Unit,
    viewModel: CharacterDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.character))
                },
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = stringResource(R.string.navigate_back),
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        when {
            uiState.isLoading || uiState.character == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            else -> {
                CharacterDetailContent(
                    character = uiState.character,
                    modifier = Modifier.padding(innerPadding),
                )
            }
        }
    }
}

@Composable
fun CharacterDetailContent(character: PlayerCharacter, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        DetailsHeader(character = character)
        Spacer(modifier = Modifier.height(16.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            DetailChip(imageVector = Icons.Default.Person, value = character.race.name)
            DetailChip(imageVector = Icons.Default.Fireplace, value = character.characterClass.name)
            DetailChip(imageVector = Icons.Default.LocalLibrary, value = character.background.name)
            DetailChip(
                imageVector = Icons.Default.Scale,
                value = stringResource(character.alignment.stringResourceId())
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        AbilityScores(character)
        Spacer(modifier = Modifier.height(16.dp))
        CombatStats(character)
    }
}

@Composable
fun DetailChip(imageVector: ImageVector, value: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier.height(24.dp),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun CombatStats(character: PlayerCharacter) {
    Column {
        Text(
            text = stringResource(R.string.combat_stats),
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(6.dp))
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp,
            ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(16.dp),
            ) {
                CombatStatItem(
                    iconRes = R.drawable.heart,
                    label = stringResource(R.string.hit_points),
                    value = "${character.hitPointsCurrent} / ${character.hitPointsMax}",
                    modifier = Modifier.weight(1f)
                ) {
                    Spacer(modifier = Modifier.height(4.dp))

                    HitPointIndicator(
                        currentHitPoints = character.hitPointsCurrent,
                        maxHitPoints = character.hitPointsMax,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
                }
                VerticalDivider(
                    modifier = Modifier.fillMaxHeight()
                )
                CombatStatItem(
                    iconRes = R.drawable.stats_shield,
                    label = stringResource(R.string.armor_class),
                    value = "${character.armorClass}",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun CombatStatItem(
    iconRes: Int,
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    additionalContent: (@Composable () -> Unit)? = null,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.height(24.dp),
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
            ),
        )
        Text(
            value,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
        additionalContent?.invoke()
    }
}

@Composable
fun HitPointIndicator(currentHitPoints: Int, maxHitPoints: Int, modifier: Modifier = Modifier) {
    val progress = if (maxHitPoints > 0) {
        (currentHitPoints.toFloat() / maxHitPoints)
            .coerceIn(0f, 1f)
    } else {
        0f
    }

    LinearProgressIndicator(
        progress = { progress },
        modifier = modifier,
        drawStopIndicator = {}
    )
}

@Composable
fun AbilityScores(character: PlayerCharacter) {
    val abilityScores = listOf(
        AbilityScoreUiModel(
            label = stringResource(R.string.ability_score_str),
            score = character.strength
        ),
        AbilityScoreUiModel(
            label = stringResource(R.string.ability_score_dex),
            score = character.dexterity
        ),
        AbilityScoreUiModel(
            label = stringResource(R.string.ability_score_con),
            score = character.constitution
        ),
        AbilityScoreUiModel(
            label = stringResource(R.string.ability_score_int),
            score = character.intelligence
        ),
        AbilityScoreUiModel(
            label = stringResource(R.string.ability_score_wis),
            score = character.wisdom
        ),
        AbilityScoreUiModel(
            label = stringResource(R.string.ability_score_cha),
            score = character.charisma
        )
    )

    Column {
        Text(
            text = stringResource(R.string.ability_scores),
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(6.dp))
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp,
            ),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(16.dp),
            ) {
                abilityScores.forEachIndexed { index, (label, value) ->
                    AbilityScoreItem(
                        value = value,
                        label = label,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    )

                    if (index < abilityScores.lastIndex) {
                        VerticalDivider(
                            modifier = Modifier.fillMaxHeight()
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun AbilityScoreItem(
    label: String,
    value: Int,
    modifier: Modifier = Modifier,
    abilityModifier: Int? = null,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(label)
        Text(value.toString())
        abilityModifier?.let {
            Text(abilityModifier.toString())
        }
    }
}

@Composable
fun DetailsHeader(character: PlayerCharacter) {
    Row {
        CharacterAvatar(
            size = 100,
            imageRes = null,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                character.name,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                ),
            )
            Row {
                Text(
                    text = "${
                        stringResource(
                            R.string.character_level,
                            character.level
                        )
                    } ${character.characterClass.name}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    ),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(R.drawable.shield),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }
}