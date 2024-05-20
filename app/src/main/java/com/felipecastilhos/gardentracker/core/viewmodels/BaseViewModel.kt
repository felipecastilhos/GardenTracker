package com.felipecastilhos.gardentracker.core.viewmodels

import androidx.lifecycle.ViewModel
import com.felipecastilhos.gardentracker.core.coroutines.CoroutineContextProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(
    private val dispatcherProvider: CoroutineContextProvider,
): ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext = dispatcherProvider.main

    protected fun launchOnIO(block: suspend CoroutineScope.() -> Unit) {
        launch(dispatcherProvider.IO) {
            block()
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }
}