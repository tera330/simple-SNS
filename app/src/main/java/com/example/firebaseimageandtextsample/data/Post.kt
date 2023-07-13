package com.example.firebaseimageandtextsample.data

import com.google.firebase.storage.StorageReference

data class Post(
    // val uid: String,
    val title: String,
    val body: String,
    val image: StorageReference? = null,
    //val author: DocumentReference,
    //val createTime: Timestamp,
    val likeCount: Int // いいねされた数
)