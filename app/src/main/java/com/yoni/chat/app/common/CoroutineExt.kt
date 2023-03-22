package com.yoni.chat.app.common

import java.util.concurrent.CancellationException

inline fun <T> runCatchingCancellable(block: () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (e: Throwable) {
        if (e is CancellationException) {
            throw e
        }
        Result.failure(e)
    }
}

inline infix fun <T> Result<T>.catch(handler: (e: Throwable) -> Unit) {
    onFailure {
        handler(it)
    }
}