package com.felipecastilhos.gardentracker.features.home

import com.felipecastilhos.gardentracker.core.coroutines.CoroutineContextProvider
import com.felipecastilhos.gardentracker.core.mvi.MVI
import com.felipecastilhos.gardentracker.core.mvi.mvi
import com.felipecastilhos.gardentracker.core.viewmodels.BaseViewModel
import com.felipecastilhos.gardentracker.features.home.HomeDestinations.*
import com.felipecastilhos.gardentracker.features.home.HomeScreenContract.*
import com.felipecastilhos.gardentracker.features.home.HomeScreenContract.UiAction.*
import com.felipecastilhos.gardentracker.features.home.HomeScreenContract.UiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    dispatcherProvider: CoroutineContextProvider,
) : BaseViewModel(dispatcherProvider = dispatcherProvider),
    MVI<UiAction, UiState, SideEffect> by mvi(Success(HOME)) {

    override fun onAction(uiAction: UiAction) {
        when (uiAction) {
            NavigateToHome -> updateUiState(Success(HOME))
            NavigateToPlants -> updateUiState(Success(PLANTS))
            NavigateToTools -> updateUiState(Success(TOOLS))
        }
    }
}
