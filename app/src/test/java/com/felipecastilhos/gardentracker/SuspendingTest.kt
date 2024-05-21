package com.felipecastilhos.gardentracker

import org.junit.Rule

internal abstract class SuspendingTest {
    @get:Rule
    val mainDispatcherRule = TestDispatcherRule()
}
