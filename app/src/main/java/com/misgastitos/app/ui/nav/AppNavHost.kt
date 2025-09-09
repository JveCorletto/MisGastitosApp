package com.misgastitos.app.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.misgastitos.app.ui.screens.LoginScreen
import com.misgastitos.app.ui.screens.RegisterScreen
import com.misgastitos.app.ui.screens.HomeScreen
import com.misgastitos.app.ui.auth.AuthViewModel

sealed class Route(val path: String) {
    data object Gate : Route("gate")
    data object Login : Route("login")
    data object Register : Route("register")
    data object Home : Route("home")
    data object Add : Route("add") // si ya tienes AddExpenseScreen
}

@Composable
fun AppNavHost() {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = Route.Gate.path
    ) {
        // Gate: decide a dónde ir según sesión
        composable(Route.Gate.path) {
            val vm: AuthViewModel = hiltViewModel()
            LaunchedEffect(Unit) {
                nav.navigate(if (vm.isLoggedIn()) Route.Home.path else Route.Login.path) {
                    popUpTo(Route.Gate.path) { inclusive = true }
                }
            }
            Box(Modifier.fillMaxSize()) // puedes poner un Splash aquí
        }

        composable(Route.Login.path) {
            LoginScreen(
                onGoRegister = { nav.navigate(Route.Register.path) },
                onLoggedIn = {
                    nav.navigate(Route.Home.path) {
                        popUpTo(Route.Login.path) { inclusive = true }
                    }
                }
            )
        }

        composable(Route.Register.path) {
            RegisterScreen(
                onRegistered = {
                    nav.navigate(Route.Home.path) {
                        popUpTo(Route.Register.path) { inclusive = true }
                    }
                },
                onGoLogin = { nav.popBackStack() }
            )
        }

        composable(Route.Home.path) {
            HomeScreen(onAdd = { nav.navigate(Route.Add.path) })
        }
    }
}