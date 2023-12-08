package com.example.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * This abstract class provides a base implementation for ViewModels in the application.
 * It defines generic types for the ViewState, ViewIntent, and SideEffect used by the ViewModel.
 */
abstract class BaseViewModel<VS : ViewState, VI : ViewIntent, SE : SideEffect>(initialState: VS) :
    ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<VS> get() = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SE>()

    val sideEffect: MutableSharedFlow<SE> get() = _sideEffect
    abstract fun sendIntent(intent: VI)

    protected suspend fun emitStateUpdate(newState: VS) {
        _state.emit(newState)
    }
}
