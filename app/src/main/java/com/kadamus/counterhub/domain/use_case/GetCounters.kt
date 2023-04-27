package com.kadamus.counterhub.domain.use_case

import com.kadamus.counterhub.data.CounterRepository
import com.kadamus.counterhub.domain.model.Counter
import kotlinx.coroutines.flow.Flow

class GetCounters(private val counterRepository: CounterRepository) {

    operator fun invoke(): Flow<List<Counter>> {
        return counterRepository.getAllCounters()
    }
}