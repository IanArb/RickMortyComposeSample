package com.ianarbuckle.rickmortycompose.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

data class CoroutineDispatchers(
    val main: CoroutineDispatcher,
    val computation: CoroutineDispatcher,
    val io: CoroutineDispatcher
) {

    @Inject
    constructor(): this(Dispatchers.Main, Dispatchers.Default, Dispatchers.IO)
}