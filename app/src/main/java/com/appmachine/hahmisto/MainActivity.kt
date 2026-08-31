package com.appmachine.hahmisto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appmachine.hahmisto.data.settings.ThemeMode
import com.appmachine.hahmisto.ui.AppViewModelProvider
import com.appmachine.hahmisto.ui.settings.SettingsViewModel
import com.appmachine.hahmisto.ui.theme.HahmistoTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val vm: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory)
            val settingsUIState by vm.settingsUiState.collectAsState()

            HahmistoTheme(settingsUIState.themeMode) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    HahmistoApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    vm: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val themeMode = vm.settingsUiState.collectAsState().value.themeMode

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        CenterAlignedTopAppBar(
            title = {
                Image(
                    painter = painterResource(R.drawable.app_logo),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .height(36.dp),
                    colorFilter = if (themeMode == ThemeMode.DARK) ColorFilter.tint(
                        MaterialTheme.colorScheme.tertiary
                    ) else null
                )
            },
            actions = {
                IconButton(onClick = {
                    val newMode =
                        if (themeMode == ThemeMode.DARK) ThemeMode.LIGHT else ThemeMode.DARK
                    vm.updateThemeMode(newMode)
                }) {
                    Icon(
                        imageVector = if (themeMode == ThemeMode.DARK) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = stringResource(R.string.theme_toggle),
                    )
                }
            },
            navigationIcon = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HahmistoTheme(themeMode = ThemeMode.SYSTEM) {

    }
}