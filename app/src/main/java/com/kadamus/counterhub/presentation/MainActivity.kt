package com.kadamus.counterhub.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kadamus.counterhub.R
import com.kadamus.counterhub.domain.model.Counter
import com.kadamus.counterhub.presentation.counters.SimpleCounter
import com.kadamus.counterhub.presentation.ui.theme.CounterHubTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val counters = viewModel.counters.collectAsStateWithLifecycle()
            val isNewCounterDialogOpen =
                viewModel.isNewCounterDialogOpen.collectAsStateWithLifecycle()
            MainContent(
                counters = counters,
                onAddNewCounter = viewModel::onAddNewCounter,
                onCloseNewCounterDialog = viewModel::closeNewCounterDialog,
                onCountChanged = viewModel::onCountChanged,
                onCounterRemoved = viewModel::onCounterRemoved,
                addCounter = viewModel::addNewCounter,
                isNewCounterDialogOpen = isNewCounterDialogOpen
            )
        }
    }
}

@Composable
@ExperimentalMaterial3Api
private fun MainContent(
    counters: State<List<Counter>>,
    onCountChanged: (counterId: Int, num: Int) -> Unit,
    onCounterRemoved: (counterId: Int) -> Unit,
    onAddNewCounter: () -> Unit,
    onCloseNewCounterDialog: () -> Unit,
    addCounter: (title: String) -> Unit,
    isNewCounterDialogOpen: State<Boolean>
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
                actions = { AppBarActions() },
                colors = TopAppBarDefaults.smallTopAppBarColors()
            )
        },
        floatingActionButton = { AddCounterFab(onAddNewCounter) },
        content = { paddingVals ->
            CounterHubTheme {
                CountersColumn(
                    paddingVals,
                    counters,
                    onCountChanged,
                    onCounterRemoved
                )
            }
        }
    )
    if (isNewCounterDialogOpen.value) {
        TitleInputDialog(onCloseNewCounterDialog, addCounter)
    }
}

@ExperimentalMaterial3Api
@Composable
private fun TitleInputDialog(
    onCloseNewCounterDialog: () -> Unit,
    addCounter: (title: String) -> Unit
) {
    var titleText by rememberSaveable { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = {
            onCloseNewCounterDialog()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onCloseNewCounterDialog()
                    addCounter(titleText)
                }
            ) {
                Text(text = stringResource(id = R.string.btn_confirm))
            }
        },
        text = {
            OutlinedTextField(
                value = titleText,
                onValueChange = { titleText = it },
                label = { Text(text = stringResource(id = R.string.lbl_counter_name)) }
            )
        },
        title = {
            Text(
                text = stringResource(id = R.string.lbl_new_counter),
                style = MaterialTheme.typography.titleLarge
            )
        }
    )
}

@Composable
fun AppBarActions() {
    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = stringResource(id = R.string.cd_add_counter)
        )
    }
    IconButton(onClick = { /*TODO*/ }) {
        Icon(imageVector = Icons.Outlined.MoreVert, contentDescription = "Menu")
    }
}

@Composable
fun CountersColumn(
    paddingValues: PaddingValues,
    counters: State<List<Counter>>,
    onCountChanged: (counterId: Int, num: Int) -> Unit,
    onCounterRemoved: (counterId: Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
    ) {
        items(counters.value) { counter ->
            SimpleCounter(
                counter = counter,
                onCountChanged = onCountChanged,
                onCounterRemoved = onCounterRemoved
            )
        }
    }

}

@Composable
fun AddCounterFab(onAddNewCounter: () -> Unit) {
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        onClick = { onAddNewCounter() }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.cd_add_counter)
        )
    }
}

@Preview(showBackground = true)
@Composable
@ExperimentalMaterial3Api
fun DefaultPreview() {
    val isDialogOpen = rememberSaveable { mutableStateOf(false) }
    val counters = rememberSaveable { mutableStateOf(listOf<Counter>()) }
    CounterHubTheme {
        MainContent(
            counters = counters,
            onCountChanged = { _, _ -> },
            onCounterRemoved = {},
            onAddNewCounter = { isDialogOpen.value = true },
            onCloseNewCounterDialog = { isDialogOpen.value = false },
            addCounter = {
                val currentCounters = counters.value
                val newCounters = currentCounters.toMutableList()
                newCounters.add(Counter(1, it, 0))
                counters.value = newCounters
            },
            isNewCounterDialogOpen = isDialogOpen
        )
    }
}

@Preview(name = "User Input Dialog")
@Composable
@ExperimentalMaterial3Api
private fun AlertDialogPreview() {
    CounterHubTheme {
        TitleInputDialog(onCloseNewCounterDialog = {}, addCounter = {})
    }
}
