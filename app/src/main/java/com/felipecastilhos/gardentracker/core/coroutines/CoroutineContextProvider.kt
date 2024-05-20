package com.felipecastilhos.gardentracker.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class to abstract the direct usage of dispatcher
 * This helps to use inject coroutines in tests
 */
interface CoroutineContextProvider {
    /**
    +-----------------------------------+
    | Main thread on Android, interact  |
    | with the UI and perform light     |
    | work                              |
    +-----------------------------------+
    | - Calling suspend functions       |
    | - Call UI functions               |
    | - Updating LiveData               |
    +-----------------------------------+
     */
    val main: CoroutineDispatcher

    /**
    +-----------------------------------+
    | Optimized for disk and network IO |
    | off the main thread               |
    +-----------------------------------+
    | - Database                        |
    | - Reading/writing files           |
    | - Networking                      |
    +-----------------------------------+
     */
    val IO: CoroutineDispatcher
}

@Singleton
class DefaultCoroutineContextProvider @Inject constructor() : CoroutineContextProvider {
    override val main: CoroutineDispatcher = Dispatchers.Main
    override val IO: CoroutineDispatcher =  Dispatchers.IO
}