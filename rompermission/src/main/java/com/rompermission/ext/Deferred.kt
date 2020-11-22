package com.rompermission.ext

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun <T> Deferred<T>.onComplete(scope: CoroutineScope = GlobalScope,
                               context: CoroutineContext = EmptyCoroutineContext,
                               start: CoroutineStart = CoroutineStart.DEFAULT,
                               block: (T) -> Unit): Job {
    return scope.launch(context, start) {
        flowOf(await()).collect {
            block.invoke(it)
        }
    }
}