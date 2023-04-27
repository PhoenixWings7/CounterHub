package com.kadamus.counterhub.data

import com.kadamus.counterhub.data.local_source.CounterDao
import com.kadamus.counterhub.domain.model.Counter
import kotlinx.coroutines.flow.Flow

class CounterRepositoryImpl(private val counterDao: CounterDao): CounterRepository {
    override fun getAllCounters(): Flow<List<Counter>> {
        return counterDao.getCounters()
    }

    override suspend fun addCounter(counter: Counter) {
        counterDao.addCounter(counter)
    }

}