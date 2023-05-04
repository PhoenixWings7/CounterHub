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

    override suspend fun deleteCounterById(counterId: Int) {
        counterDao.deleteCounter(counterId)
    }

    override suspend fun incrementCounterWithId(id: Int, incrementBy: Int) {
        counterDao.incrementCounter(id, incrementBy)
    }

    override suspend fun updateCounter(counter: Counter) {
        counterDao.updateCounter(counter)
    }
}