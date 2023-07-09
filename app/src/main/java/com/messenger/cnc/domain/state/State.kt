package com.messenger.cnc.domain

import com.messenger.cnc.domain.state.Action

/**
 * Highlighter for state. Can be [PendingState], [SuccessState], [ErrorState]
 */
interface State {}

class PendingState(val action: Action? = null): State {}
class SuccessState(val action: Action? = null): State {}

/**
 * Parameter: error - represent occurred exception
 */
class ErrorState(val error: Exception): State {}