package com.messenger.cnc.domain.errors

class UsernameIsNotAvailableException: Exception() {
    val errorMessage = "This username is not available"
}