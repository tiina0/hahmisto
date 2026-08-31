package com.appmachine.hahmisto.ui.character.form

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appmachine.hahmisto.R
import com.appmachine.hahmisto.domain.model.CharacterAlignment
import com.appmachine.hahmisto.ui.AppViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterFormScreen(
    onCancel: () -> Unit,
    onSaved: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CharacterFormViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.characterUiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                CharacterFormEvent.Saved -> onSaved()
            }
        }
    }

    Scaffold(
        bottomBar = {
            Surface(
                modifier = Modifier.navigationBarsPadding()
            ) {
                Button(
                    onClick = {
                        viewModel.saveCharacter(uiState.characterDetails)
                    },
                    enabled = uiState.isCharacterValid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)


                ) {
                    Text(stringResource(R.string.save))
                }
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(R.string.new_character))
                },
                navigationIcon = {
                    IconButton(
                        onClick = onCancel
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.cancel),
                        )
                    }
                }
            )
        },
        modifier = modifier,
    ) { innerPadding ->
        val scrollState = rememberScrollState()
       Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState)
        ) {
           OutlinedTextField(
                    suffix = {
                        Icon(
                            imageVector = Icons.Default.PersonOutline,
                            contentDescription = null,
                        )
                    },
                    value = uiState.characterDetails.name,
                    onValueChange = {
                        viewModel.updateName(it)
                    },
                    label = { Text(stringResource(R.string.character_name)) },
                    modifier = modifier
                        .fillMaxWidth(),
                    enabled = true,
                    singleLine = true
                )

            Spacer(modifier = Modifier.height(12.dp))

            FormDropdown(
                label = stringResource(R.string.race),
                items = uiState.races,
                selectedItem = uiState.races.find {
                    it.id == uiState.characterDetails.raceId
                },
                onItemSelected = {
                    viewModel.updateRace(it.id)
                },
                itemLabel = {
                    it.name
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            FormDropdown(
                label = stringResource(R.string.character_class),
                items = uiState.characterClasses,
                selectedItem = uiState.characterClasses.find {
                    it.id == uiState.characterDetails.classId
                },
                onItemSelected = {
                    viewModel.updateClass(it.id)
                },
                itemLabel = {
                    it.name
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            FormDropdown(
                label = stringResource(R.string.background),
                items = uiState.backgrounds,
                selectedItem = uiState.backgrounds.find {
                    it.id == uiState.characterDetails.backgroundId
                },
                onItemSelected = {
                    viewModel.updateBackground(it.id)
                },
                itemLabel = {
                    it.name
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            LevelField(
                level = uiState.characterDetails.level,
                onLevelChanged = { newLevel ->
                    viewModel.updateLevel(newLevel)
                }
//              shorthand:  onLevelChanged = viewModel::updateLevel,
            )

            Spacer(modifier = Modifier.height(12.dp))

            AbilityScoreFields(
                viewModel = viewModel,
                details = uiState.characterDetails
            )

            Spacer(modifier = Modifier.height(12.dp))

            NumberField(
                value = uiState.characterDetails.maxHitPoints,
                onValueChange = viewModel::updateMaxHitPoints,
                label = stringResource(R.string.max_hit_points),
                minValue = 1,
            )

            Spacer(modifier = Modifier.height(12.dp))

            NumberField(
                value = uiState.characterDetails.armorClass,
                onValueChange = viewModel::updateArmorClass,
                label = stringResource(R.string.armor_class),
                minValue = 1,
            )

            Spacer(modifier = Modifier.height(12.dp))

            AlignmentSelector(
                selectedAlignment = uiState.characterDetails.alignment,
                onAlignmentSelected = viewModel::updateAlignment,
            )
        }
    }
}

@Composable
fun AbilityScoreFields(
    details: CharacterDetails,
    viewModel: CharacterFormViewModel
) {
    Column {
        Text(
            text = stringResource(R.string.ability_scores),
            style = MaterialTheme.typography.labelLarge,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                NumberField(
                    value = details.strength,
                    onValueChange = { newValue ->
                        newValue?.let(viewModel::updateStrength)
                    },
                    label = stringResource(R.string.ability_score_str)
                )
                NumberField(
                    value = details.dexterity,
                    onValueChange = { newValue ->
                        newValue?.let(viewModel::updateDexterity)
                    },
                    label = stringResource(R.string.ability_score_dex)
                )
                NumberField(
                    value = details.constitution,
                    onValueChange = { newValue ->
                        newValue?.let(viewModel::updateConstitution)
                    },
                    label = stringResource(R.string.ability_score_con)
                )

            }
            Spacer(modifier = Modifier.width(4.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                NumberField(
                    value = details.intelligence,
                    onValueChange = { newValue ->
                        newValue?.let(viewModel::updateIntelligence)
                    },
                    label = stringResource(R.string.ability_score_int)
                )
                NumberField(
                    value = details.wisdom,
                    onValueChange = { newValue ->
                        newValue?.let(viewModel::updateWisdom)
                    },
                    label = stringResource(R.string.ability_score_wis)
                )
                NumberField(
                    value = details.charisma,
                    onValueChange = { newValue ->
                        newValue?.let(viewModel::updateCharisma)
                    },
                    label = stringResource(R.string.ability_score_cha)
                )

            }
        }
    }
}

@Composable
fun AlignmentSelector(
    selectedAlignment: CharacterAlignment?,
    onAlignmentSelected: (CharacterAlignment) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(stringResource(R.string.alignment))
        FlowRow(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            maxItemsInEachRow = 3,
        )
        {
            CharacterAlignment.entries.forEach { alignment ->
                FilterChip(
                    selected = alignment == selectedAlignment,
                    onClick = {
                        onAlignmentSelected(alignment)
                    },
                    label = {
                        Text(alignment.toDisplayName())
                    }
                )

            }
        }
    }

}

@Composable
fun NumberField(
    value: Int?,
    onValueChange: (Int?) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    minValue: Int = 0,
    maxValue: Int? = null
) {
    OutlinedTextField(
        value = value?.toString().orEmpty(),
        onValueChange = { text ->
            val parsedValue = text.toIntOrNull()

            if (text.isEmpty()) {
                onValueChange(null)
            } else if (
                parsedValue != null &&
                parsedValue >= minValue &&
                (maxValue == null || parsedValue <= maxValue)
            ) {
                onValueChange(parsedValue)
            }
        },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
        ),
        trailingIcon = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = stringResource(R.string.increase_value, label),
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            val newValue = if (value == null) {
                                minValue
                            } else {
                                value + 1
                            }

                            if (maxValue == null || newValue <= maxValue) {
                                onValueChange(newValue)
                            }
                        },
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = stringResource(R.string.decrease_value, label),
                    modifier = Modifier
                        .size(20.dp)
                        .clickable(
                            enabled = value != null && value > minValue,
                        ) {
                            onValueChange((value!! - 1).coerceAtLeast(minValue))
                        },
                )
            }
        },
        modifier = modifier,
        singleLine = true,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> FormDropdown(
    label: String,
    items: List<T>,
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    itemLabel: (T) -> String,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = it
        },
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = selectedItem?.let(itemLabel).orEmpty(),
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded,
                )
            },
            modifier = Modifier
                .menuAnchor(
                    type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                    enabled = true,
                )
                .fillMaxWidth(),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(itemLabel(item))
                    },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }

        }
    }
}

@Composable
fun LevelField(
    level: Int,
    onLevelChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.level),
            style = MaterialTheme.typography.labelLarge,
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.small,

                    ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = {
                    onLevelChanged(level - 1)
                },
                enabled = level > 1,
            ) {

                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = stringResource(R.string.decrease_level),
                )
            }

            Text(
                text = level.toString(),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
            )

            IconButton(
                onClick = {
                    onLevelChanged(level + 1)
                },
                enabled = level < 20,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.increase_level)
                )
            }
        }
    }

}
