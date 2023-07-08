package com.messenger.cnc.domain.errors

import java.lang.Exception

class AddFriendsExeption(errorMessage: String = "Can`t add to friend"): Exception() {}