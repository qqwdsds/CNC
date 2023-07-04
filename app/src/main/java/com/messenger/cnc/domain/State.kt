package com.messenger.cnc.domain

/**
 * Highlighter for state. Can be [PendingState], [SuccessState], [ErrorState]
 */
interface State {}

class PendingState: State {}
class SuccessState: State {}

/**
 * Parameter: error - represent occurred exception
 */
class ErrorState(val error: Exception): State {}