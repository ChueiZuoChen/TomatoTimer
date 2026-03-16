package com.example.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

enum class TimerMode(val initialSeconds: Int) {
    FOCUS(25 * 60),
    SHORT_BREAK(5 * 60),
    LONG_BREAK(15 * 60)
}

data class TimerUiState(
    val currentMode: TimerMode = TimerMode.FOCUS,
    val remainingSeconds: Int = TimerMode.FOCUS.initialSeconds,
    val isRunning: Boolean = false,
    val isFinished: Boolean = false
) {
    val progress: Float
        get() = remainingSeconds.toFloat() / currentMode.initialSeconds.toFloat()

    val formattedTime: String
        get() {
            val minutes = remainingSeconds / 60
            val seconds = remainingSeconds % 60
            return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        }
}

class TimerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TimerUiState())
    val uiState: StateFlow<TimerUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    fun switchMode(mode: TimerMode) {
        stopTimer()
        _uiState.update {
            it.copy(
                currentMode = mode,
                remainingSeconds = mode.initialSeconds,
                isRunning = false,
                isFinished = false
            )
        }
    }

    fun toggleTimer() {
        if (_uiState.value.isRunning) {
            pauseTimer()
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        if (_uiState.value.remainingSeconds <= 0) {
            resetTimer()
        }
        
        _uiState.update { it.copy(isRunning = true, isFinished = false) }
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_uiState.value.remainingSeconds > 0) {
                delay(1000L)
                _uiState.update { 
                    val newSeconds = it.remainingSeconds - 1
                    it.copy(
                        remainingSeconds = newSeconds,
                        isFinished = newSeconds == 0
                    )
                }
            }
            _uiState.update { it.copy(isRunning = false) }
        }
    }

    private fun pauseTimer() {
        stopTimer()
        _uiState.update { it.copy(isRunning = false) }
    }

    fun resetTimer() {
        stopTimer()
        _uiState.update {
            it.copy(
                remainingSeconds = it.currentMode.initialSeconds,
                isRunning = false,
                isFinished = false
            )
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }
    
    fun onFinishedEventHandled() {
        _uiState.update { it.copy(isFinished = false) }
    }
}
