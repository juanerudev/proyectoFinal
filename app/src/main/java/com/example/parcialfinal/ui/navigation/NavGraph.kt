package com.example.parcialfinal.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.parcialfinal.ui.screens.*

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController = navController)
        }

        composable("people") {
            PeopleListScreen(navController = navController)
        }

        composable(
            route = "person/{personId}",
            arguments = listOf(navArgument("personId") { type = NavType.IntType })
        ) { backStackEntry ->
            val personId = backStackEntry.arguments?.getInt("personId") ?: 1
            PersonDetailScreen(personId = personId, navController = navController)
        }

        composable("films") {
            FilmsListScreen(navController = navController)
        }

        composable(
            route = "film/{filmId}",
            arguments = listOf(navArgument("filmId") { type = NavType.IntType })
        ) { backStackEntry ->
            val filmId = backStackEntry.arguments?.getInt("filmId") ?: 1
            FilmDetailScreen(filmId = filmId, navController = navController)
        }

        composable("planets") {
            PlanetsListScreen(navController = navController)
        }

        composable("starships") {
            StarshipsListScreen(navController = navController)
        }

        composable("vehicles") {
            VehiclesListScreen(navController = navController)
        }
    }
}