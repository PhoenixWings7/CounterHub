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

            DailyReminderSetting(
                isChecked = isChecked.value,
                onCheckedChange = viewModel::toggleDailyReminder,
                onTimePicked = viewModel::setDailyReminderTime
            )
        }
    }

}