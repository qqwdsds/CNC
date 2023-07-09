package com.messenger.cnc.domain.state

sealed class Result<T>
class PendingResult<T>() : Result<T>()
class SuccessResult<T>(
    val data: T? = null
) : Result<T>()
class ErrorResult<T>(
    val error: Exception
) : Result<T>()