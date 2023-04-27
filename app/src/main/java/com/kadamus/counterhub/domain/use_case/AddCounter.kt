package com.kadamus.counterhub.domain.use_case

import com.kadamus.counterhub.data.CounterRepository
import com.kadamus.counterhub.domain.model.Counter
import com.kadamus.counterhub.exceptions.CounterAppException

class AddCounter(private val counterRepository: CounterRepository) {
    @Throws(CounterAppException.InvalidTitleException::class)
    suspend operator fun invoke(counter: Counter) {
        if (counter.title.isBlank())
            throw CounterAppException.InvalidTitleException()
        counterRepository.addCounter(counter)
    }
}