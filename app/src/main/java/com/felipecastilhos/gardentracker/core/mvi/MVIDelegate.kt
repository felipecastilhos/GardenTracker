package com.felipecastilhos.gardentracker.core.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MVIDelegate<UiAction, UiState, SideEffect> internal constructor(
    initialUiState: UiState,
) : MVI<UiAction, UiState, SideEffect> {
    private val _uiState = MutableStateFlow(initialUiState)
    override val uiState: StateFlow<UiState>
        get() = _uiState.asStateFlow()

    private val _sideEffect by lazy { Channel<SideEffect>() }
    override val sideEffect: Flow<SideEffect> by lazy { _sideEffect.receiveAsFlow() }

    override fun CoroutineScope.emitSideEffect(effect: SideEffect) {
        this.launch {
            _sideEffect.send(effect)
        }
    }

    override fun updateUiState(newUiState: UiState) = _uiState.update { newUiState }
    override fun updateUiState(block: UiState.() -> UiState) = _uiState.update(block)
    override fun onAction(uiAction: UiAction) = Unit
}

fun <UiState, UiAction, SideEffect> mvi(
    initialUiState: UiState,
): MVI<UiAction, UiState, SideEffect> = MVIDelegate(initialUiState = initialUiState)