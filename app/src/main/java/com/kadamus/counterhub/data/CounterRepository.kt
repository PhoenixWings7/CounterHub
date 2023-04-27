package com.kadamus.counterhub.data

import com.kadamus.counterhub.domain.model.Counter
import kotlinx.coroutines.flow.Flow

interface CounterRepository {
    fun getAllCounters(): Flow<List<Counter>>
    suspend fun addCounter(counter: Counter)
}