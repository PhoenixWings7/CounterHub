package com.kadamus.counterhub.presentation

import androidx.lifecycle.ViewModel
import com.kadamus.counterhub.domain.Counter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _isNewCounterDialogOpen: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isNewCounterDialogOpen: StateFlow<Boolean> = _isNewCounterDialogOpen
    private val _counters: MutableStateFlow<List<Counter>> = MutableStateFlow(listOf())
    val counters: StateFlow<List<Counter>> = _counters

    fun onAddNewCounter() {
        // show alert dialog
        _isNewCounterDialogOpen.value = true
    }

    fun closeNewCounterDialog() {
        _isNewCounterDialogOpen.value = false
    }

    fun addNewCounter(title: String) {
        // TODO
    }

    fun onCountChanged(counterId: Int, num: Int) {
        // TODO
    }

    fun onCounterRemoved(counterId: Int) {}
}