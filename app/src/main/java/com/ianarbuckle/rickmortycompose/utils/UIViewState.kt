package com.ianarbuckle.rickmortycompose.utils

sealed class UIViewState<out T: Any> {
    data class Success<out T: Any>(val result: T): UIViewState<T>()
    object Error: UIViewState<Nothing>()
    object Loading: UIViewState<Nothing>()
}