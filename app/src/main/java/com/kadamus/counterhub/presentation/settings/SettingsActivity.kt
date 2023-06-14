package com.kadamus.counterhub.presentation.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : ComponentActivity() {
    private val viewModel: SettingsViewModel by viewModels()

    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isChecked = viewModel.dailyReminderSetting
                .collectAsStateWithLifecycle(initialValue = false)
            val reminderTime = viewModel.dailyReminderTime
                .collectAsStateWithLifecycle(initialValue = null)

            DailyReminderSetting(
                isChecked = isChecked.value,
                onCheckedChange = viewModel::toggleDailyReminder,
                dailyReminderTime = reminderTime.value,
                onTimePicked = viewModel::setDailyReminderTime
            )
        }
    }

}