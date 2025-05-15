package com.project.middleman.core.source.data.sealedclass

sealed class RequestState<out T> {
    data object Loading: RequestState<Nothing>()
    data class Success<T>(val data: T?): RequestState<T>()
    data class Error(val error: Throwable): RequestState<Nothing>()
}