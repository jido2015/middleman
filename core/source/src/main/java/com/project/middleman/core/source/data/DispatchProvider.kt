package com.project.middleman.core.source.data

import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Singleton
interface DispatchProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}