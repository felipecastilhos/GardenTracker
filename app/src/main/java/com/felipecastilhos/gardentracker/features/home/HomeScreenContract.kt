package com.felipecastilhos.gardentracker.features.home

interface HomeScreenContract {
    sealed class UiState {
        data class Success(val destination: HomeDestinations) : UiState()
    }

    sealed interface UiAction {
        data object NavigateToHome : UiAction
        data object NavigateToPlants : UiAction
        data object NavigateToTools : UiAction
    }

    sealed interface SideEffect
}