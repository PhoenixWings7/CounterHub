package com.kadamus.counterhub.presentation.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
    val dailyReminderTime: Flow<Pair<Int, Int>?> = dataStore.data.map { preferences: Preferences ->
        val timeString = preferences[DAILY_REMINDER_TIME]
        val timeData = timeString?.split(':')
        if (timeData != null && timeData.size == 2)
            Pair<Int, Int>(timeData[0].toInt(), timeData[1].toInt())
        else
            null
    }

    companion object {
        private val DAILY_REMINDER = booleanPreferencesKey("daily_reminder")
        private val DAILY_REMINDER_TIME = stringPreferencesKey("daily_reminder_time")
    }

    fun toggleDailyReminder(isChecked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.edit {
                it[DAILY_REMINDER] = isChecked
            }
        }
    }

    fun setDailyReminderTime(hours: Int, minutes: Int) {
        viewModelScope.launch {
            dataStore.edit {
                it[DAILY_REMINDER_TIME] = "$hours:$minutes"
            }
        }
    }
}