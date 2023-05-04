package com.kadamus.counterhub.domain.use_case

import com.kadamus.counterhub.data.CounterRepository
import com.kadamus.counterhub.domain.model.Counter

class UpdateCounter(private val counterRepository: CounterRepository) {
    suspend operator fun invoke(counter: Counter) {
        counterRepository.updateCounter(counter)
    }
}