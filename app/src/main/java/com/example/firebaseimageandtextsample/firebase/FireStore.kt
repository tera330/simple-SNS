package com.example.firebaseimageandtextsample.firebase

import android.util.Log
import com.example.firebaseimageandtextsample.ItemListAdapter
import com.example.firebaseimageandtextsample.data.Post
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class FireStore {
    private val db = Firebase.firestore
    private val storage = Firebase.storage
    private val storageRef = storage.reference
    private val addressList = mutableListOf<String>("1", "2", "3")
    private val postList = mutableListOf<Post>()

    private val userDocumentRef = db.collection("users")
    private val userRef = userDocumentRef.document(uid!!)

    // プロフィールの作成
    fun createProfile(macAddress: String, name: String, introduction: String) {
        Log.d("createProfile", userRef.toString())
        Log.d("createProfile", uid.toString())
        userRef
            .set(mapOf(
                "macAddress" to macAddress,
                "name" to name,
                "introduction" to introduction,
                "createTime" to FieldValue.serverTimestamp(),
                "likePostCount" to 0
            ))
    }

    // 投稿
    fun post(title: String, body: String) {
        val postRef = userRef.collection("post").document() // ドキュメントIDを自動生成
            .set(mapOf(
                "title" to title,
                "body" to body,
                //"author" to userRef,
                //"createTime" to FieldValue.serverTimestamp(),
                //"updateTime" to FieldValue.serverTimestamp(),
                "likeCount" to 0 // いいねされた数
            ))
        val imageRef = postRef.toString()
    }

    // todo 自分がいいねを押したさいに、usersドキュメント以下にlikedPostsを作成

    // 他ユーザーがいいねを押したとき todo ボタンを押したときに引き薄を渡す
    fun addLikedUserToPost(postId: String, userId: String) {
        val postRef = db.collection("post").document(postId) // その投稿の参照を取得

        // サブコレクションを追加
        postRef.collection("likedUsers").document(userId)
            .set(mapOf(
                "id" to userId, // いいねをつけたユーザーのid
                "createTime" to FieldValue.serverTimestamp()
            ))
    }

    // いいねを取得
    fun getLikedUsers(postId: String) {
        // いいねしたユーザー最大２０件のドキュメント
        val likedUserSnapshot = db.collection("post").document(postId)
            .collection("likedUsers")
            .orderBy("createTime", Query.Direction.DESCENDING)
            .limit(20)
            .get()

        // todo 非同期処理に対応してユーザーの参照を取得
        // todo ページング処理
    }

    fun getData(itemListAdapter: ItemListAdapter) {
        userDocumentRef
            .addSnapshotListener { snapshot, e -> // users
                for (userDocument in snapshot!!.documents) {
                    val address = userDocument.getString("macAddress")
                        if (addressList.contains(address!!)) {
                            val matchUid = userDocument.id
                            val postRef = userDocumentRef.document(matchUid).collection("post")
                            postRef
                                .get()
                                .addOnSuccessListener { querySnapshot ->
                                    for (documentSnapshot in querySnapshot.documents) {
                                        val title = documentSnapshot.getString("title")
                                        val body = documentSnapshot.getString("body")

                                        val userPost = Post(
                                            title = title!!,
                                            body = body!!,
                                            likeCount = 0
                                        )
                                        if (!postList.contains(userPost)) {
                                            postList.add(userPost)
                                        }
                                    }
                                    itemListAdapter.submitList(postList)
                                }
                        }
                }
            }
    }



    /*
    fun getData(itemListAdapter: ItemListAdapter) {
        userDocumentRef
            .addSnapshotListener { snapshot, e ->
                for (document in snapshot!!) {
                    val address = document.getString("address")
                    if (address in addressList) {
                        val matchId = document.id // addressが一致したコレクションのドキュメントID
                        val matchName = document.getString("name")
                        val userImageRef = storageRef.child(matchId)

                        val profile = Profile(
                            Uid = matchId,
                            Text = matchName,
                            Image = userImageRef
                        )
                        profileList.add(profile)
                    }
                }
                itemListAdapter.submitList(profileList)
            }
    }

     */
}