package com.messenger.cnc.domain

interface State {}

class PendingState: State {}
class SuccessState: State {}
class ErrorState(val error: Exception): State {}