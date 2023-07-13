package com.example.firebaseimageandtextsample.data

import java.sql.Timestamp

data class Profile(
    val macAddress: String,
    val name: String,
    val introduction: String,
    val createTime: Timestamp,
    val updateTime: Timestamp,
    val likePostCount: Int
)
