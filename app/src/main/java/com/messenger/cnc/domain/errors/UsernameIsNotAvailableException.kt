package com.messenger.cnc.domain.errors

class UsernameIsNotAvailableException(errorMessage: String = "This username is not available"): Exception(errorMessage) {}