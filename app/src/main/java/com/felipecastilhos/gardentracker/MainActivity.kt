package com.felipecastilhos.gardentracker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.felipecastilhos.gardentracker.core.mvi.CollectSideEffect
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenContract.SideEffect.NewPlantHasBeenAdded
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenContract.UiAction
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

            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    uiState.plants.forEach {
                        PlantName(it)
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Button(onClick = { viewModel.onAction(UiAction.AddNewPlant) }) {
                        Text(text = "Update")
                    }
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