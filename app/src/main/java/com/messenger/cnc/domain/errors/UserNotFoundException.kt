package com.messenger.cnc.domain.errors

class UserNotFoundException(errorMessage: String = "User not found"): Exception(errorMessage) {}