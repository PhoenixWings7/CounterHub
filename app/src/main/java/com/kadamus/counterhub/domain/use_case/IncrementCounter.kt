package com.kadamus.counterhub.domain.use_case

import com.kadamus.counterhub.data.CounterRepository

class IncrementCounter(private val counterRepository: CounterRepository) {
    suspend operator fun invoke(counterId: Int, incrementBy: Int = 1) {
        counterRepository.incrementCounterWithId(counterId, incrementBy)
    }
}