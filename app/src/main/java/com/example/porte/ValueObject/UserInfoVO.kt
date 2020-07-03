package com.example.porte.ValueObject

import android.media.Image
import androidx.room.PrimaryKey

data class UserInfoVO(
    val userID: String,
    val userName: String,
    val userImg: Image
)