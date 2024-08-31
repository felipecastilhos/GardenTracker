package com.felipecastilhos.gardentracker.features.home

import app.cash.turbine.test
import com.felipecastilhos.gardentracker.SuspendingTest
import com.felipecastilhos.gardentracker.TestCoroutineContextProvider
import com.felipecastilhos.gardentracker.features.home.HomeDestinations.*
import com.felipecastilhos.gardentracker.features.home.HomeScreenContract.UiAction.*
import com.felipecastilhos.gardentracker.features.home.HomeScreenContract.UiState.Success
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test


internal class MyHomeViewModelTest : SuspendingTest() {

    private class TestSubject(val homeViewModel: HomeViewModel) {
        companion object {
            fun createWith(contextProvider: TestCoroutineContextProvider) =
                TestSubject(HomeViewModel(contextProvider))
        }
    }

    @Test
    fun `display home when none navigation intent happened`() = runTest {
        val testSubject = TestSubject.createWith(contextProvider)
        testSubject.homeViewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(Success(HOME))
        }
    }

    @Test
    fun `navigate to home when navigate to home intent is received`() = runTest {
        val testSubject = TestSubject.createWith(contextProvider)
        with(testSubject) {
            homeViewModel.uiState.test {
                homeViewModel.onAction(NavigateToPlants)
                homeViewModel.onAction(NavigateToHome)
                assertThat(expectMostRecentItem()).isEqualTo(Success(HOME))

            }
        }
    }

    @Test
    fun `navigate to plants when navigate to plants intent is received`() = runTest {
        val testSubject = TestSubject.createWith(contextProvider)
        with(testSubject) {
            homeViewModel.uiState.test {
                skipItems(1) // skip initial value
                homeViewModel.onAction(NavigateToPlants)
                assertThat(awaitItem()).isEqualTo(Success(PLANTS))
            }
        }
    }

    @Test
    fun `navigate to tools when navigate to tools intent is received`() = runTest {
        val testSubject = TestSubject.createWith(contextProvider)
        with(testSubject) {
            homeViewModel.uiState.test {
                skipItems(1) // skip initial value
                homeViewModel.onAction(NavigateToTools)
                assertThat(awaitItem()).isEqualTo(Success(TOOLS))
            }
        }
    }
}