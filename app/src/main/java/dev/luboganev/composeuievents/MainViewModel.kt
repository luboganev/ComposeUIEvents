package dev.luboganev.composeuievents

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _liveData = MutableLiveData<Int?>()
    val liveData: LiveData<Int?> = _liveData

    private val _stateFlow = MutableStateFlow<Int?>(null)
    val stateFlow: StateFlow<Int?> = _stateFlow

    private val _sharedFlow = MutableSharedFlow<Int?>()
    val sharedFlow: SharedFlow<Int?> = _sharedFlow

    private val _sharedFlow1 = MutableSharedFlow<Int?>(replay = 1)
    val sharedFlow1: SharedFlow<Int?> = _sharedFlow1

    var withDelayState = mutableStateOf(false)

    fun postLiveData() {
        _liveData.postValue(1)
        if (withDelayState.value) {
            viewModelScope.launch {
                delay(DELAY_MILLIS)
                _liveData.postValue(null)
            }
        } else {
            _liveData.postValue(null)
        }
    }

    fun setLiveData() {
        _liveData.value = 1
        if (withDelayState.value) {
            viewModelScope.launch {
                delay(DELAY_MILLIS)
                _liveData.value = null
            }
        } else {
            _liveData.value = null
        }
    }

    fun setStateFlow() {
        _stateFlow.value = 1
        if (withDelayState.value) {
            viewModelScope.launch {
                delay(DELAY_MILLIS)
                _stateFlow.value = null
            }
        } else {
            _stateFlow.value = null
        }
    }

    fun tryEmitSharedFlow() {
        _sharedFlow.tryEmit(1)
        if (withDelayState.value) {
            viewModelScope.launch {
                delay(DELAY_MILLIS)
                _sharedFlow.tryEmit(null)
            }
        } else {
            _sharedFlow.tryEmit(null)
        }
    }

    fun emitSharedFlow() {
        viewModelScope.launch {
            _sharedFlow.emit(1)
            if (withDelayState.value) {
                launch {
                    delay(DELAY_MILLIS)
                    _sharedFlow.emit(null)
                }
            } else {
                _sharedFlow.emit(null)
            }
        }
    }

    fun tryEmitSharedFlow1() {
        _sharedFlow1.tryEmit(1)
        if (withDelayState.value) {
            viewModelScope.launch {
                delay(DELAY_MILLIS)
                _sharedFlow1.tryEmit(null)
            }
        } else {
            _sharedFlow1.tryEmit(null)
        }
    }

    fun emitSharedFlow1() {
        viewModelScope.launch {
            _sharedFlow1.emit(1)
            if (withDelayState.value) {
                launch {
                    delay(DELAY_MILLIS)
                    _sharedFlow1.emit(null)
                }
            } else {
                _sharedFlow1.emit(null)
            }
        }
    }

    companion object {
        private const val DELAY_MILLIS = 1000L
    }
}