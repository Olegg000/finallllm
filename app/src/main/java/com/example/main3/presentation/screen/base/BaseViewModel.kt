package com.example.main3.presentation.screen.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.main3.common.AppLogger
import com.example.net.types.ResultType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE>(val initState: STATE): ViewModel() {

    private val _state = MutableStateFlow(initState)
    val state: StateFlow<STATE> = _state.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val loadingCount = MutableStateFlow(0)
    val isLoading: StateFlow<Boolean> = loadingCount.map { it > 0 }.stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(5000),false)

    private val _nav = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val nav = _nav.asSharedFlow()

    val logger = AppLogger(this::class.simpleName ?: "ViewModel")

    protected fun update(upd: (STATE) -> STATE) {
        _state.update(upd)
    }

    protected fun navigate(r: String) {
        _nav.tryEmit(r)
    }

    protected fun <T> field(upd: STATE.(T) -> STATE): (T) -> Unit = { t ->
        _state.update { it.upd(t) }
    }

    open fun init() {

    }

    protected fun <T> launchWithStatus(
        call: suspend () -> ResultType<T>,
        onSuccess: STATE.(T) -> STATE
    ) {
        viewModelScope.launch {
            try {
                loadingCount.value++
                _error.value = null
                delay(5000L)

                val res = call().getOrThrow()

                _state.update { it.onSuccess(res) }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                loadingCount.value--
            }
        }
    }

     fun clearError() {
         _error.value = null
    }

}