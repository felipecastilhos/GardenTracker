package com.felipecastilhos.gardentracker.features.mygarden

import app.cash.turbine.test
import com.felipecastilhos.gardentracker.SuspendingTest
import junit.framework.TestCase.assertEquals
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
        assertEquals("Loading", viewModel.viewState.value)
    }

    @Test
    fun `fetch a new message`() = runTest {
        viewModel.viewState.test {
            assertEquals("Loading", awaitItem())
            assertEquals("My Garden", awaitItem())
        }
    }
}