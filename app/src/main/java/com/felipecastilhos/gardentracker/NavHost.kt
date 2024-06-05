package com.felipecastilhos.gardentracker

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.felipecastilhos.gardentracker.features.HomeScreen
import com.felipecastilhos.gardentracker.features.MyPlantsScreen
import com.felipecastilhos.gardentracker.features.ToolsScreen
import kotlinx.serialization.Serializable

interface Route {
    val route: String
    operator fun invoke(): String = route
}

@Serializable
sealed class HomeScreen(override val route: String) : Route {
    data object Home : HomeScreen("home")
    data object MyPlants : HomeScreen("my_plants")
    data object Tools : HomeScreen("tools")
}

@Composable
fun MainNavGraph(
    onNavigate: (Route) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Route = HomeScreen.Home,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination()
    ) {
        mainGraph(modifier = modifier, onNavigate = onNavigate)
    }
}

fun NavGraphBuilder.mainGraph(
    modifier: Modifier = Modifier,
    onNavigate: (Route) -> Unit,
) {
    composable(route = HomeScreen.Home.route) {
        HomeScreen(modifier = modifier, onNavigate)
    }

    composable(route = HomeScreen.MyPlants.route) {
        MyPlantsScreen(modifier = modifier, onNavigate)
    }

    composable(route = HomeScreen.Tools.route) {
        ToolsScreen(modifier = modifier, onNavigate)
    }
}