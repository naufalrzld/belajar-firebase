package com.belajar.firebase

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    val id: String,
    val name: String,
    val age: Int,
    val gender: String
) : Parcelable