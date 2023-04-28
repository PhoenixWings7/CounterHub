package com.kadamus.counterhub.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kadamus.counterhub.domain.model.Counter
import com.kadamus.counterhub.domain.use_case.AddCounter
import com.kadamus.counterhub.domain.use_case.DeleteCounter
import com.kadamus.counterhub.domain.use_case.GetCounters
import com.kadamus.counterhub.exceptions.CounterAppException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCounters: GetCounters,
    private val addCounter: AddCounter,
    private val deleteCounter: DeleteCounter
) : ViewModel() {

    private companion object {
        const val LOG_TAG = "CounterHub -> MainViewModel"
    }

    private val _isNewCounterDialogOpen: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isNewCounterDialogOpen: StateFlow<Boolean> = _isNewCounterDialogOpen
    private val _counters: MutableStateFlow<List<Counter>> = MutableStateFlow(listOf())
    val counters: StateFlow<List<Counter>> = _counters

    init {
        viewModelScope.launch {
            getCounters().collect {
                _counters.emit(it)
            }
        }
    }

    fun onAddNewCounter() {
        // show alert dialog
        _isNewCounterDialogOpen.value = true
    }

    fun closeNewCounterDialog() {
        _isNewCounterDialogOpen.value = false
    }

    fun addNewCounter(title: String) {
        viewModelScope.launch {
            try {
                addCounter(title)
            } catch (e: CounterAppException) {
                Log.e(LOG_TAG, e.message ?: e.uiMessage)
            }
        }
    }

    fun onCountChanged(counterId: Int, num: Int) {
        // TODO
    }

    fun onCounterRemoved(counterId: Int) {
        viewModelScope.launch {
            deleteCounter(counterId)
        }
    }
}