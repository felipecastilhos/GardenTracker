package com.felipecastilhos.gardentracker.core.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felipecastilhos.gardentracker.core.coroutines.CoroutineContextProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(
    private val dispatcherProvider: CoroutineContextProvider,
) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext = dispatcherProvider.main

    protected fun launchOnMain(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            block()
        }
    }

    protected fun launchOnIO(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(dispatcherProvider.IO) {
            block()
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
        dispatcherProvider.IO.cancel()
    }
}