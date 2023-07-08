package com.messenger.cnc.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val name: String,
    val description: String,
    val image: String): Parcelable {
    // HACK for firebase
    constructor(): this("","","","")
}