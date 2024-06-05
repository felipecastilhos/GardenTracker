package com.felipecastilhos.gardentracker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.felipecastilhos.gardentracker.HomeScreen.*
import com.felipecastilhos.gardentracker.core.mvi.CollectSideEffect
import com.felipecastilhos.gardentracker.features.HomeDestinations
import com.felipecastilhos.gardentracker.features.HomeDestinations.*
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenContract
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenContract.SideEffect.NewPlantHasBeenAdded
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenContract.UiAction
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenContract.UiAction.AddNewPlant
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenContract.UiAction.ListPlants
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenContract.UiState.*
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MyGardenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val sideEffect = viewModel.sideEffect
            val context = LocalContext.current
            val navHostController: NavHostController = rememberNavController()

            CollectSideEffect(sideEffect = sideEffect) {
                when (it) {
                    NewPlantHasBeenAdded -> Toast.makeText(
                        context,
                        "New Plant added",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            val uiState by viewModel.uiState.collectAsState()
            viewModel.onAction(ListPlants)

            var currentDestinations by rememberSaveable { mutableStateOf(HOME) }

            NavigationSuiteScaffold(
                navigationSuiteItems = {
                    HomeDestinations.entries.forEach {
                        item(icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = it.icon),
                                contentDescription = stringResource(id = it.contentDescription),
                            )
                        },
                            label = { Text(text = stringResource(id = it.label)) },
                            selected = it == currentDestinations,
                            onClick = {
                                if (currentDestinations != it) {
                                    currentDestinations = it

                                    when (currentDestinations) {
                                        HOME -> navHostController.navigate(Home())
                                        PLANTS -> navHostController.navigate(MyPlants())
                                        TOOLS -> navHostController.navigate(Tools())
                                    }
                                }
                            }
                        )
                    }
                }
            ) {
                MainNavGraph(
                    modifier = Modifier.fillMaxSize(),
                    navController = navHostController,
                    onNavigate = { navHostController.navigate(it.route) }
                )
            }
        }
    }
}


@Composable
private fun Content(onAction: (UiAction) -> Unit, uiState: MyGardenContract.UiState) {
    when (uiState) {
        is Error ->
            Text(text = "Error")

        Loading ->
            Text(text = "Loading")

        is Success -> {
            val state = uiState
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                state.plants.forEach {
                    PlantName(it)
                }
                Spacer(modifier = Modifier.height(15.dp))
                Button(onClick = { onAction(AddNewPlant) }) {
                    Text(text = "Update")
                }
            }

        }
    }
}

@Composable
fun PlantName(name: String) = Text(text = name)

@Preview(showBackground = true)
@Composable
fun GreetingsPreview() = PlantName("My Garden")