package com.kadamus.counterhub.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kadamus.counterhub.R

@Composable
@ExperimentalMaterial3Api
fun DailyReminderSetting(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    dailyReminderTime: Pair<Int, Int>?,
    onTimePicked: (hours: Int, minutes: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isTimePickerOpen by rememberSaveable { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(is24Hour = true)

    Column(
        modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // daily reminder on/off switch
        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Daily reminder", style = MaterialTheme.typography.titleLarge)
            Switch(
                modifier = Modifier.minimumInteractiveComponentSize(),
                checked = isChecked, onCheckedChange = {
                    onCheckedChange(it)
                }
            )
        }
        if (isChecked) {
            // display the time set for the reminder
            Row(
                modifier = Modifier.clickable { isTimePickerOpen = true },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(0.dp, 0.dp, 4.dp, 0.dp),
                    text = stringResource(id = R.string.lbl_daily_reminder_time),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = if (dailyReminderTime != null)
                        "${dailyReminderTime.first}:${dailyReminderTime.second}" else "not set",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (isTimePickerOpen) {
                TimePickerDialog(
                    onCloseTimePicker = { isTimePickerOpen = false },
                    timePickerState = timePickerState,
                    onTimePicked = onTimePicked
                )
            }

        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun TimePickerDialog(
    onCloseTimePicker: () -> Unit,
    timePickerState: TimePickerState,
    onTimePicked: (hours: Int, minutes: Int) -> Unit
) {
    AlertDialog(onDismissRequest = { onCloseTimePicker() }) {
        ElevatedCard {
            TimePicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), state = timePickerState
            )
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { onCloseTimePicker() }) {
                    Text(text = "Cancel")
                }
                TextButton(onClick = {
                    onTimePicked(timePickerState.hour, timePickerState.minute)
                    onCloseTimePicker()
                }) {
                    Text(text = "Confirm")
                }

            }
        }
    }
}

@Preview
@Composable
@ExperimentalMaterial3Api
fun ReminderPreview() {
    var isChecked by rememberSaveable { mutableStateOf(true) }
    var reminderTime by rememberSaveable {
        mutableStateOf(Pair(12, 15))
    }
    DailyReminderSetting(
        isChecked,
        onCheckedChange = { isChecked = it },
        reminderTime,
        onTimePicked = { h, m -> reminderTime = Pair(h, m) })
}