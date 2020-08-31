package com.ianarbuckle.rickmortycompose.ui.characters

import com.ianarbuckle.rickmortycompose.api.Character

sealed class UIViewState<out T: Any> {
    data class Success<out T: Any>(val result: T): UIViewState<T>()
    object Error: UIViewState<Nothing>()
    object Loading: UIViewState<Nothing>()
}