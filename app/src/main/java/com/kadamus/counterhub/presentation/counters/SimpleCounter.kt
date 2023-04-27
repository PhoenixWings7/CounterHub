package com.kadamus.counterhub.presentation.counters

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kadamus.counterhub.R
import com.kadamus.counterhub.domain.model.Counter
import com.kadamus.counterhub.presentation.ui.theme.CounterHubTheme

@Composable
fun SimpleCounter(
    counter: Counter,
    onCountChanged: (counterId: Int, num: Int) -> Unit,
    onCounterRemoved: (counterId: Int) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp, 0.dp, 12.dp, 16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = counter.title,
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(onClick = { onCounterRemoved(counter.id) }) {
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
fun CounterPreview() {
    CounterHubTheme {
        SimpleCounter(
            counter = Counter(0, "Push-ups", 3),
            onCountChanged = { _, _ -> }
        ) {}
    }
}