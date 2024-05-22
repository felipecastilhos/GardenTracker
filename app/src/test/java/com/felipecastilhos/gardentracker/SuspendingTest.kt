package com.felipecastilhos.gardentracker

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Rule

internal abstract class SuspendingTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()
    val contextProvider = TestCoroutineContextProvider(testDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = TestDispatcherRule(testDispatcher)
}
