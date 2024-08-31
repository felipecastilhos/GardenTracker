package com.felipecastilhos.gardentracker.features.mygarden

import app.cash.turbine.test
import com.felipecastilhos.gardentracker.SuspendingTest
import com.felipecastilhos.gardentracker.TestCoroutineContextProvider
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenUiContract.*
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenUiContract.UiAction.ListPlants
import com.felipecastilhos.gardentracker.features.mygarden.MyGardenUiContract.UiState.Loading
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Test

internal class MyGardenViewModelTest : SuspendingTest() {
    private class UnitUnderTest(
        val viewModel: MyGardenViewModel,
        val myGardenRepositoryDependency: MyGardenRepository,
    ) {
        companion object {
            fun getUnitUnderTest(
                contextProvider: TestCoroutineContextProvider,
                myGardenRepositoryDependency: MyGardenRepository = mockk {
                    every { listPlants() } coAnswers {
                        Result.success(listOf("Plant 1"))
                    }
                    every { addPlant(any()) } returns Result.success(
                        listOf(
                            "Plant 1",
                            "Plant 2"
                        )
                    )
                },
            ): UnitUnderTest {
                val viewModel = MyGardenViewModel(
                    dispatcherProvider = contextProvider,
                    myGardenRepository = myGardenRepositoryDependency
                )
                return UnitUnderTest(
                    myGardenRepositoryDependency = myGardenRepositoryDependency,
                    viewModel = viewModel,
                )
            }
        }
    }

    @Test
    fun `start viewmodel with loading state`() {
        val expectedList = listOf("Plant 1")
        runTest {
            val myGardenRepository = mockk<MyGardenRepository> {
                every { listPlants() } coAnswers {
                    Result.success(expectedList)
                }
            }

            val viewModel = MyGardenViewModel(contextProvider, myGardenRepository)
            viewModel.uiState.test {
                assertEquals(Loading, awaitItem())
            }
        }
    }

    @Test
    fun `LoadPlants action fetches the plant list `() {
        val expectedList = listOf("Plant 1")
        runTest {
            val myGardenRepository = mockk<MyGardenRepository> {
                every { listPlants() } coAnswers {
                    delay(3000)
                    Result.success(expectedList)
                }
            }

            val viewModel = MyGardenViewModel(contextProvider, myGardenRepository)
            viewModel.uiState.test {
                skipItems(1) //skip loading
                viewModel.onAction(ListPlants)
                assertEquals(UiState.Success(expectedList), awaitItem())
            }
        }
    }

    @Test
    fun `LoadPlants action send Loading UI state before fetching new plants  `() {
        val expectedList = listOf("Plant 1")
        runTest {
            val myGardenRepository = mockk<MyGardenRepository> {
                every { listPlants() } coAnswers {
                    Result.success(expectedList)
                }
            }

            val viewModel = MyGardenViewModel(contextProvider, myGardenRepository)
            viewModel.uiState.test {
                skipItems(1) // Skip loading
                viewModel.onAction(ListPlants)
                skipItems(1) // Skip the first LoadPlants result
                viewModel.onAction(ListPlants)
                assertEquals(Loading, awaitItem())
                assertEquals(UiState.Success(expectedList), awaitItem())
            }
        }
    }

    @Test
    fun `Set as UiState Error if load plant fails`() {
        val exception = Exception("Service error")
        val unitUnderTest =
            UnitUnderTest.getUnitUnderTest(contextProvider, myGardenRepositoryDependency = mockk {
                every { listPlants() } coAnswers {
                    Result.failure(
                        exception
                    )
                }
            })

        val viewModel = unitUnderTest.viewModel

        runTest {
            viewModel.onAction(ListPlants)
            viewModel.uiState.test {
                assertEquals(UiState.Error(exception), awaitItem())
            }
        }
    }

    @Test
    fun `AddNewPlant add a new plant to the plants list`() = runTest {
        val viewModel = UnitUnderTest.getUnitUnderTest(contextProvider).viewModel
        viewModel.onAction(UiAction.AddNewPlant)
        viewModel.uiState.test {
            assertEquals(UiState.Success(plants = listOf("Plant 1", "Plant 2")), awaitItem())
        }
    }

    @Test
    fun `AddNewPlant action send a NewPlantHasBeenAdded side effect`() = runTest {
        val viewModel = UnitUnderTest.getUnitUnderTest(contextProvider).viewModel
        viewModel.onAction(UiAction.AddNewPlant)
        viewModel.sideEffect.test {
            assertEquals(SideEffect.NewPlantHasBeenAdded, awaitItem())
        }
    }

    @Test
    fun `Set as UiState Error if add new plant fails`() {
        val exception = Exception("Service error")
        val unitUnderTest = UnitUnderTest.getUnitUnderTest(contextProvider)
        every { unitUnderTest.myGardenRepositoryDependency.addPlant(any()) } returns Result.failure(
            exception
        )
        val viewModel = unitUnderTest.viewModel

        runTest {
            viewModel.onAction(UiAction.AddNewPlant)
            viewModel.uiState.test {
                assertEquals(UiState.Error(exception), awaitItem())
            }
        }
    }
}