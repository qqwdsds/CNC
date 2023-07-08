package com.messenger.cnc.domain

import com.messenger.cnc.domain.state.StateDataTypes

/**
 * Highlighter for state. Can be [PendingState], [SuccessState], [ErrorState]
 */
interface State {}

class PendingState(val data: StateDataTypes? = null): State {}
class SuccessState(val data: StateDataTypes? = null): State {}

/**
 * Parameter: error - represent occurred exception
 */
class ErrorState(val error: Exception): State {}