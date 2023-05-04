package com.kadamus.counterhub.data

import com.kadamus.counterhub.domain.model.Counter
import kotlinx.coroutines.flow.Flow

interface CounterRepository {
    fun getAllCounters(): Flow<List<Counter>>
    suspend fun addCounter(counter: Counter)
    suspend fun deleteCounterById(id: Int)
    suspend fun incrementCounterWithId(id: Int, incrementBy: Int = 1)
    suspend fun updateCounter(counter: Counter)
}