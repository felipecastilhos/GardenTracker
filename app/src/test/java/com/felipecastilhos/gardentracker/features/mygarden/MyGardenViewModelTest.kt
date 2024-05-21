package com.felipecastilhos.gardentracker.features.mygarden

import app.cash.turbine.test
import com.felipecastilhos.gardentracker.SuspendingTest
import com.felipecastilhos.gardentracker.core.coroutines.CoroutineContextProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

internal class MyGardenViewModelTest : SuspendingTest() {
    @MockK
    lateinit var coroutineContextProvider: CoroutineContextProvider

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: MyGardenViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery { coroutineContextProvider.IO } returns testDispatcher
        coEvery { coroutineContextProvider.main } returns testDispatcher
        viewModel = MyGardenViewModel(coroutineContextProvider)
    }

    @Test
    fun `start viewmodel with loading state`() {
        assertEquals("Loading", viewModel.viewState.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `fetch a new message`(): Unit = runTest {
        viewModel.viewState.test {
            assertEquals("Loading", awaitItem())
            assertEquals("My Garden", awaitItem())
        }
    }
}