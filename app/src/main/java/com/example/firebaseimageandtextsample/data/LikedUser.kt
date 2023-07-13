package com.example.firebaseimageandtextsample.data

import com.google.firebase.firestore.ServerTimestamp

data class LikedUser(
    val id: String, //いいねを付けたユーザーのID
    val createTime: ServerTimestamp
)
