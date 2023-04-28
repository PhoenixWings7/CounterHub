package com.kadamus.counterhub.domain.use_case

import com.kadamus.counterhub.data.CounterRepository

class DeleteCounter(private val counterRepository: CounterRepository) {
    suspend operator fun invoke(counterId: Int) {
        counterRepository.deleteCounterById(counterId)
    }
}