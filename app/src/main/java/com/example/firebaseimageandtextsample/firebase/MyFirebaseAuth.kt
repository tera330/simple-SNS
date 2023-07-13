package com.example.firebaseimageandtextsample.firebase

import com.google.firebase.auth.FirebaseAuth

private var _uid: String?= null
val uid get() = _uid

class MyFirebaseAuth {
    private val auth = FirebaseAuth.getInstance()

    fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _uid = auth.currentUser?.uid
            }
    }

    fun logIn(email: String, password: String) {
        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    _uid = auth.currentUser?.uid
                }
        }
    }

}