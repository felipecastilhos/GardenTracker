package com.felipecastilhos.gardentracker.features.mygarden

import com.felipecastilhos.gardentracker.core.coroutines.CoroutineContextProvider
import com.felipecastilhos.gardentracker.core.viewmodels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyGardenViewModel @Inject constructor(
    private val dispatcherProvider: CoroutineContextProvider,
) : BaseViewModel(
    dispatcherProvider
) {
    private val message: String = "My Garden"
    private val _viewState: MutableStateFlow<String?> = MutableStateFlow("Loading")
    val viewState: StateFlow<String?> by lazy { _viewState.asStateFlow() }

    init {
        getMessage()
    }

    private fun getMessage() {
        launchOnIO {
            delay(800)
            _viewState.value = message
        }
    }

    fun updateMessage(newMessage: String) {
        launchOnMain {
            _viewState.value = newMessage
        }
    }
}