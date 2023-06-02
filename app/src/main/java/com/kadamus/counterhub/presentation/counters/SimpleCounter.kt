package com.kadamus.counterhub.presentation.counters

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kadamus.counterhub.R
import com.kadamus.counterhub.domain.model.Counter
import com.kadamus.counterhub.presentation.ui.theme.CounterHubTheme

/**
 * A basic counter with editable title.
 *
 * @param counter The counter to be displayed.
 * @param onCountChanged A function to be invoked when the count is being adjusted. Passes counter id
 *      & a number to be added to the count (can be a negative number).
 * @param onCounterRemoved A function to be invoked when user wants to delete the counter.
 * @param onTitleChanged A function to be invoked when user wants to save the new counter title.
 *      Passes the counter in its unchanged form & the new title.
 */
@Composable
@ExperimentalMaterial3Api
fun SimpleCounter(
    counter: Counter,
    onCountChanged: (counterId: Int, num: Int) -> Unit,
    onCounterRemoved: (counterId: Int) -> Unit,
    onTitleChanged: (counter: Counter, newTitle: String) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp, 0.dp, 12.dp, 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                var titleText by rememberSaveable { mutableStateOf(counter.title) }
                var isTitleInEdit by rememberSaveable { mutableStateOf(false) }
                val focusManager = LocalFocusManager.current
                val focusRequester = remember { FocusRequester() }

                BasicTextField(
                    value = titleText,
                    onValueChange = { titleText = it },
                    textStyle = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            isTitleInEdit = it.isFocused
                        }
                )

                // show "save" & "cancel" btns if title is being edited, otherwise "delete counter" btn
                if (isTitleInEdit) {
                    // save btn
                    IconButton(onClick = {
                        onTitleChanged(counter, titleText)
                        focusManager.clearFocus()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Done,
                            contentDescription = stringResource(id = R.string.cd_save)
                        )
                    }
                    // cancel edit btn
                    IconButton(
                        modifier = Modifier.minimumInteractiveComponentSize(),
                        onClick = {
                            titleText = counter.title
                            focusManager.clearFocus()
                        }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = stringResource(id = R.string.cd_save)
                        )
                    }
                } else
                // delete counter btn
                    IconButton(
                        modifier = Modifier.minimumInteractiveComponentSize(),
                        onClick = { onCounterRemoved(counter.id) }) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = stringResource(id = R.string.cd_delete_counter)
                        )
                    }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledIconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = { onCountChanged(counter.id, -1) }) {
                    Icon(
                        imageVector = Icons.Outlined.Remove,
                        contentDescription = stringResource(id = R.string.cd_decrease_counter)
                    )
                }
                Text(
                    modifier = Modifier.padding(16.dp, 0.dp),
                    text = counter.count.toString(), style = MaterialTheme.typography.displaySmall
                )
                FilledIconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = { onCountChanged(counter.id, 1) }) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = stringResource(id = R.string.cd_increase_counter)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
@ExperimentalMaterial3Api
fun CounterPreview() {
    CounterHubTheme {
        SimpleCounter(
            counter = Counter(0, "Push-ups", 3),
            onCountChanged = { _, _ -> },
            onCounterRemoved = {},
            onTitleChanged = { _, _ -> })
    }
}