package com.felipecastilhos.gardentracker.features.mygarden

interface MyGardenContract {
    sealed class UiState {
        object Loading : UiState()
        data class Success(val plants: List<String>) : UiState()
        data class Error(val exception: Throwable?) : UiState()
    }

    sealed interface UiAction {
        data object ListPlants : UiAction
        data object AddNewPlant : UiAction
    }

    sealed interface SideEffect {
        data object NewPlantHasBeenAdded : SideEffect
    }
}



