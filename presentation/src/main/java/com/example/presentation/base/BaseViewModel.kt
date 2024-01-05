package com.example.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * This abstract class provides a base implementation for ViewModels in the application.
 * It defines generic types for the ViewState, ViewIntent, and SideEffect used by the ViewModel.
 */
abstract class BaseViewModel<VS : ViewState, VI : ViewIntent, SE : SideEffect> :
    ViewModel() {
    private val _state = MutableStateFlow(this.initialValue())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SE>()

    val sideEffect: SharedFlow<SE> get() = _sideEffect

    abstract fun sendIntent(intent: VI)

    protected suspend fun emitStateUpdate(newState: VS) {
        _state.emit(newState)
    }

    protected suspend fun emitEffect(newEffect: SE) {
        _sideEffect.emit(newEffect)
    }

    protected abstract fun initialValue(): VS
}
