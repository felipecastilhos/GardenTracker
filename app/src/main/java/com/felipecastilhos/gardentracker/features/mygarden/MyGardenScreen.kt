package com.felipecastilhos.gardentracker.features.mygarden

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.felipecastilhos.gardentracker.core.mvi.CollectSideEffect
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenUiContract.UiAction
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenUiContract.UiAction.AddNewPlant
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenUiContract.UiState.Error
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenUiContract.UiState.Loading
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenUiContract.UiState.Success

@Composable
fun MyGardenScreen(modifier: Modifier = Modifier, viewModel: MyGardenViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val sideEffect = viewModel.sideEffect
    val context = LocalContext.current

    viewModel.onAction(UiAction.ListPlants)

    CollectSideEffect(sideEffect) {
        when (it) {
            MyGardenUiContract.SideEffect.NewPlantHasBeenAdded -> Toast.makeText(
                context,
                "New Plant added",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Content(
        modifier = modifier,
        onAction = { viewModel.onAction(UiAction.AddNewPlant) },
        uiState = uiState
    )
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    onAction: (UiAction) -> Unit,
    uiState: MyGardenUiContract.UiState
) {
    when (uiState) {
        is Success -> {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                uiState.plants.forEach {
                    PlantName(it)
                }
                Spacer(modifier = Modifier.height(14.dp))
                Button(onClick = { onAction(AddNewPlant) }) {
                    Text(text = "Update")
                }
            }
        }

        is Error -> Text(text = "Error")
        Loading -> Text(text = "Loading")
    }
}

@Composable
fun PlantName(name: String) = Text(text = name)
