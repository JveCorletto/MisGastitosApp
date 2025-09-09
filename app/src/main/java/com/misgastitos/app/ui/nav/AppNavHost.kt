package com.misgastitos.app.ui.nav

import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.LaunchedEffect
import com.misgastitos.app.ui.auth.LoginScreen
import com.misgastitos.app.ui.auth.RegisterScreen
import com.misgastitos.app.ui.auth.AuthViewModel
import com.misgastitos.app.ui.screens.HomeScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.misgastitos.app.ui.category.CategoryEditScreen
import com.misgastitos.app.ui.category.CategoryListScreen

sealed class Route(val path: String) {
    data object Gate : Route("gate")
    data object Login : Route("login")
    data object Register : Route("register")
    data object Home : Route("home")
    data object Add : Route("add")

    data object Categories : Route("categories")
    data object CategoryEdit : Route("category_edit/{categoryId}") {
        fun createRoute(categoryId: Long? = null) = "category_edit/${categoryId ?: "new"}"
    }
}

@Composable
fun AppNavHost() {
    val nav = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()

    NavHost(
        navController = nav,
        startDestination = Route.Gate.path
    ) {
        composable(Route.Gate.path) {
            LaunchedEffect(Unit) {
                nav.navigate(if (authViewModel.isLoggedIn()) Route.Home.path else Route.Login.path) {
                    popUpTo(Route.Gate.path) { inclusive = true }
                }
            }
            Box(Modifier.fillMaxSize())
        }

        composable(Route.Login.path) {
            LoginScreen(
                onLoggedIn = {
                    nav.navigate(Route.Home.path) {
                        popUpTo(Route.Login.path) { inclusive = true }
                    }
                },
                onGoRegister = { nav.navigate(Route.Register.path) }
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
            HomeScreen(
                onAdd = { nav.navigate(Route.Add.path) },
                onLogout = {
                    authViewModel.logout()
                    nav.navigate(Route.Login.path) {
                        popUpTo(Route.Home.path) { inclusive = true }
                    }
                },
                onManageCategories = { nav.navigate(Route.Categories.path) }
            )
        }

        composable(Route.Categories.path) {
            CategoryListScreen(
                onAddCategory = { nav.navigate(Route.CategoryEdit.createRoute()) },
                onEditCategory = { categoryId ->
                    nav.navigate(Route.CategoryEdit.createRoute(categoryId))
                }
            )
        }

        composable(
            route = Route.CategoryEdit.path,
            arguments = listOf(navArgument("categoryId") {
                type = NavType.StringType
                nullable = true
            })
        ) { backStackEntry ->
            val categoryIdStr = backStackEntry.arguments?.getString("categoryId")
            val categoryId = if (categoryIdStr == "new") null else categoryIdStr?.toLongOrNull()

            CategoryEditScreen(
                categoryId = categoryId,
                onSave = { nav.popBackStack() },
                onCancel = { nav.popBackStack() }
            )
        }
    }
}