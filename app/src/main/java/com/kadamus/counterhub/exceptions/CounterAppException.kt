package com.kadamus.counterhub.exceptions

sealed class CounterAppException(val uiMessage: String): Exception() {
    class InvalidTitleException(): CounterAppException("The title can't be empty!")
}