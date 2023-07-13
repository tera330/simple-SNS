package com.example.firebaseimageandtextsample.data

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ServerTimestamp

data class LikedPost(
    val id: String, // 自分が言い値を付けた投稿
    val postRef: DocumentReference,
    val createTime: ServerTimestamp
)
