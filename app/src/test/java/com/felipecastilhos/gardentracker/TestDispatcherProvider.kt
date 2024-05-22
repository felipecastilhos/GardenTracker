package com.felipecastilhos.gardentracker

import com.felipecastilhos.gardentracker.core.coroutines.CoroutineContextProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class TestCoroutineContextProvider @OptIn(ExperimentalCoroutinesApi::class) constructor(
    testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : CoroutineContextProvider {
    override val main: CoroutineDispatcher = testDispatcher
    override val IO: CoroutineDispatcher = testDispatcher
}