package com.felipecastilhos.gardentracker.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.felipecastilhos.gardentracker.R
import com.felipecastilhos.gardentracker.Route
import com.felipecastilhos.gardentracker.features.home.HomeDestinations.*
import com.felipecastilhos.gardentracker.features.home.HomeScreenContract.UiAction.*
import com.felipecastilhos.gardentracker.features.home.HomeScreenContract.UiState.Success
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenScreen

@Composable
fun HomeScreen(
    onNavigate: (Route) -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by homeViewModel.uiState.collectAsState()

    val currentDestination by remember(uiState) {
        derivedStateOf {
            (uiState as Success).destination
        }
    }

    HomeScreenContent(
        modifier = modifier,
        currentDestination = currentDestination,
        onNavigationItemClicked = {
            when (it) {
                HOME -> homeViewModel.onAction(NavigateToHome)
                PLANTS -> homeViewModel.onAction(NavigateToPlants)
                TOOLS -> homeViewModel.onAction(NavigateToTools)
            }
        },
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    currentDestination: HomeDestinations,
    onNavigationItemClicked: (HomeDestinations) -> Unit,
) {
    NavigationSuiteScaffold(
        modifier = modifier,
        navigationSuiteItems = {
            createHomeNavigationSuiteBottomBarItems(currentDestination, onNavigationItemClicked)
        }
    ) {
        val destinationModifier = Modifier.fillMaxSize()
        when (currentDestination) {
            HOME -> HomeTabScreen(modifier = destinationModifier)
            PLANTS -> MyGardenScreen(modifier = destinationModifier)
            TOOLS -> ToolsTabScreen(modifier = destinationModifier)
        }
    }
}

fun NavigationSuiteScope.createHomeNavigationSuiteBottomBarItems(
    currentDestination: HomeDestinations,
    onNavigationItemClicked: (HomeDestinations) -> Unit
) {
    entries.forEach {
        item(icon = {
            BottomBarIcon(it.icon, it.contentDescription)
        },
            label = { Text(text = stringResource(id = it.label)) },
            selected = it == currentDestination,
            onClick = {
                onNavigationItemClicked(it)
            }
        )
    }
}

@Composable
fun BottomBarIcon(icon: Int, contentDescription: Int) {
    Icon(
        imageVector = ImageVector.vectorResource(id = icon),
        contentDescription = stringResource(id = contentDescription),
    )
}

@Composable
fun HomeTabScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.home))
    }
}

@Composable
fun MyPlantsTabScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.plants))
    }
}

@Composable
fun ToolsTabScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.tools))
    }
}

@Preview
@Composable
fun MyHomeTabScreenPreview() = HomeTabScreen()