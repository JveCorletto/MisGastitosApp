package com.misgastitos.app.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.misgastitos.app.ui.screens.HomeScreen
import com.misgastitos.app.ui.screens.LoginScreen
import com.misgastitos.app.ui.screens.AddExpenseScreen
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNav() {
    val nav = rememberNavController()
    NavHost(nav, startDestination = "login") {
        composable("login") { LoginScreen(onDone = {
            nav.navigate("home") { popUpTo("login") { inclusive = true } }
        }) }
        composable("home") { HomeScreen(onAdd = { nav.navigate("add") }) }
        composable("add") { AddExpenseScreen(onSaved = { nav.popBackStack() }) }
    }
}