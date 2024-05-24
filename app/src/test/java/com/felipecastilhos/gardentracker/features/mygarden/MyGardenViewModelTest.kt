package com.felipecastilhos.gardentracker.features.mygarden

import app.cash.turbine.test
import com.felipecastilhos.gardentracker.SuspendingTest
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenContract.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

internal class MyGardenViewModelTest : SuspendingTest() {
    private lateinit var viewModel: MyGardenViewModel

    @Before
    fun setUp() {
        viewModel = MyGardenViewModel(contextProvider)
    }

    @Test
    fun `start viewmodel with loading state`() = runTest {
        assertEquals(true, viewModel.uiState.value.isLoading)
    }

    @Test
    fun `Loading plants show a loading first`() = runTest {
        viewModel.uiState.test {
            assertEquals(UiState(isLoading = true, listOf()), awaitItem())
            assertEquals(UiState(isLoading = false, listOf("Plant 1")), awaitItem())
        }
    }

    @Test
    fun `Loading plants return a list containing plants`() = runTest {
        viewModel.uiState.test {
            skipItems(1) //skip the loading
            assertEquals(UiState(isLoading = false, listOf("Plant 1")), awaitItem())
        }
    }

    @Test
    fun `AddNewPlant add a new plant to the plants list`()  = runTest {
        viewModel.onAction(UiAction.AddNewPlant)
        viewModel.uiState.test {
            assertEquals(UiState(isLoading = false, plants = listOf("Plant 1")), awaitItem())
        }
    }

    @Test
    fun `AddNewPlant action send a NewPlantHasBeenAdded side effect`()  = runTest {
        viewModel.onAction(UiAction.AddNewPlant)
        viewModel.sideEffect.test {
            assertEquals(SideEffect.NewPlantHasBeenAdded, awaitItem())
        }
    }
}