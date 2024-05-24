package com.felipecastilhos.gardentracker.features.mygarden

import androidx.lifecycle.viewModelScope
import com.felipecastilhos.gardentracker.core.coroutines.CoroutineContextProvider
import com.felipecastilhos.gardentracker.core.mvi.MVI
import com.felipecastilhos.gardentracker.core.mvi.mvi
import com.felipecastilhos.gardentracker.core.viewmodels.BaseViewModel
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenContract.*
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenContract.UiAction.AddNewPlant
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenContract.UiAction.LoadPlants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class MyGardenViewModel @Inject constructor(
    dispatcherProvider: CoroutineContextProvider,
) : BaseViewModel(
    dispatcherProvider
), MVI<UiAction, UiState, SideEffect> by mvi(initialUiState = UiState()) {
    init {
        onAction(LoadPlants)
    }

    override fun onAction(uiAction: UiAction) {
        when (uiAction) {
            LoadPlants -> loadPlants()
            AddNewPlant -> addNewPlant()
        }
    }

    private fun loadPlants() {
        launchOnMain {
            delay(800)
            updateUiState { copy(isLoading = true) }
            updateUiState { copy(isLoading = false, plants = listOf(plantName("1"))) }
        }
    }

    private fun addNewPlant() =
        updateUiState {
            val newList = plants.toMutableList()
            newList.add(plantName(plants.size.inc().toString()))
            viewModelScope.emitSideEffect(SideEffect.NewPlantHasBeenAdded)
            copy(isLoading = false, plants = newList)
        }

    private fun plantName(id: String) = "Plant $id"
}