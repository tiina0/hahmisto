package com.appmachine.hahmisto

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.appmachine.hahmisto.ui.navigation.TasksNavHost

@Composable
fun HahmistoApp(navController: NavHostController = rememberNavController()) {
    TasksNavHost(navController = navController)
}