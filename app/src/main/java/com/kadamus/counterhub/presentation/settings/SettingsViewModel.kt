package com.kadamus.counterhub.presentation.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {
    private var _dailyReminderSetting: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[DAILY_REMINDER] ?: false
    }
    val dailyReminderSetting = _dailyReminderSetting

    companion object {
        private val DAILY_REMINDER = booleanPreferencesKey("daily_reminder")
    }

    fun toggleDailyReminder(isChecked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.edit {
                it[DAILY_REMINDER] = isChecked
            }
        }
    }

    fun setDailyReminderTime(hours: Int, minutes: Int) {
        //TODO("Not yet implemented")
    }
}