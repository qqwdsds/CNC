package com.messenger.cnc.data.models

data class User(
    val id: String,
    val name: String,
    val description: String,
    val image: String) {
    // HACK for firebase
    constructor(): this("","","","")
}