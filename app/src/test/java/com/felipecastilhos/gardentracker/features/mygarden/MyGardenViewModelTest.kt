package com.felipecastilhos.gardentracker.features.mygarden


import com.felipecastilhos.gardentracker.core.coroutines.CoroutineContextProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MyGardenViewModelTest {
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
    fun `test getMessage`(): Unit = runTest {
        val message = viewModel.viewState.first()
        assertEquals("Loading", message)
    }
}