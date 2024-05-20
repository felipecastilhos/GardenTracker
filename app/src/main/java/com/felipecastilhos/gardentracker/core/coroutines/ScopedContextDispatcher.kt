package com.felipecastilhos.gardentracker.core.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

/**
 * Add dispatcher provide in a context
 */
class ScopedContextDispatcher(
    val scope: CoroutineScope,
    private val dispatcherProvider: CoroutineContextProvider
) {
    val main: CoroutineContext
        get() = scope.coroutineContext + dispatcherProvider.main
    val io: CoroutineContext
        get() = scope.coroutineContext + dispatcherProvider.io
}