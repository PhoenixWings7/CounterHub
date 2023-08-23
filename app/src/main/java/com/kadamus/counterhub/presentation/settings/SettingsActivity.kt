package com.kadamus.counterhub.presentation.settings

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.kadamus.counterhub.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : ComponentActivity() {
    private val viewModel: SettingsViewModel by viewModels()

    @ExperimentalPermissionsApi
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val isChecked = viewModel.dailyReminderSetting
                .collectAsStateWithLifecycle(initialValue = false)
            val reminderTime = viewModel.dailyReminderTime
                .collectAsStateWithLifecycle(initialValue = null)
            val notificationPermissionState =
                rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)

            DailyReminderSetting(
                isChecked = isChecked.value,
                onCheckedChange = { checked ->
                    if (checked) {
                        if (!notificationPermissionState.status.isGranted)
                            notificationPermissionState.launchPermissionRequest()
                        if (notificationPermissionState.status.isGranted)
                            viewModel.toggleDailyReminder(true)
                        else {
                            Toast.makeText(
                                context,
                                getString(R.string.error_lacks_permission, "notification"),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else
                        viewModel.toggleDailyReminder(false)
                },
                dailyReminderTime = reminderTime.value,
                onTimePicked = viewModel::setDailyReminderTime
            )
        }
    }
}