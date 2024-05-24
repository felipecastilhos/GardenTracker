package com.felipecastilhos.gardentracker.features.mygarden

interface MyGardenContract {
    data class UiState(val isLoading: Boolean = true, val plants: List<String> = listOf())

    sealed interface UiAction {
        data object LoadPlants : UiAction
        data object AddNewPlant : UiAction
    }

    sealed interface SideEffect {
        data object NewPlantHasBeenAdded : SideEffect
    }
}