package com.watsonlogic.nickwalkerfans.feed.model


/**
 * A generic class that holds a value or an exception
 */
sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

fun <T> Result<T>.successOr(fallback: T): T = (this as? Result.Success<T>)?.data ?: fallback