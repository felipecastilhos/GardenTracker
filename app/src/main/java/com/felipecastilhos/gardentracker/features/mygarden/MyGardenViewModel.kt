package com.felipecastilhos.gardentracker.features.mygarden

import androidx.lifecycle.viewModelScope
import com.felipecastilhos.gardentracker.core.coroutines.CoroutineContextProvider
import com.felipecastilhos.gardentracker.core.mvi.MVI
import com.felipecastilhos.gardentracker.core.mvi.mvi
import com.felipecastilhos.gardentracker.core.viewmodels.BaseViewModel
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenUiContract.SideEffect
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenUiContract.UiAction
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenUiContract.UiAction.AddNewPlant
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenUiContract.UiAction.ListPlants
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenUiContract.UiState
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenUiContract.UiState.Error
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenUiContract.UiState.Loading
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenUiContract.UiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyGardenViewModel @Inject constructor(
    dispatcherProvider: CoroutineContextProvider,
    private val myGardenRepository: MyGardenRepository,
) : BaseViewModel(
    dispatcherProvider
), MVI<UiAction, UiState, SideEffect> by mvi(initialUiState = Loading) {
    private var count = 0;

    override fun onAction(uiAction: UiAction) {
        when (uiAction) {
            ListPlants -> listPlants()
            AddNewPlant -> addNewPlant()
        }
    }

    private fun listPlants() {
        updateUiState { Loading }
        launchOnMain {
            val result = myGardenRepository.listPlants()
            result.toUiState(onError = {
                updateUiState {
                    Error(result.exceptionOrNull())
                }
            }, onSuccess = {
                updateUiState {
                    Success(result.getOrThrow())
                }
            })
        }
    }

    private fun addNewPlant() {
        launchOnIO {
            val result = myGardenRepository.addPlant(plantName((++count).toString()))
            result.toUiState(
                onError = {
                    updateUiState {
                        Error(result.exceptionOrNull())
                    }
                },
                onSuccess = {
                    updateUiState { Success(result.getOrThrow()) }
                    viewModelScope.emitSideEffect(SideEffect.NewPlantHasBeenAdded)
                })
        }
    }

    private fun plantName(id: String) = "Plant $id"
}