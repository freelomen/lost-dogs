package com.example.lostdog.utilities

import android.net.Uri
import com.example.lostdog.models.CommonModel
import com.example.lostdog.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

lateinit var AUTH: FirebaseAuth
lateinit var CURRENT_UID: String
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var REF_STORAGE_ROOT: StorageReference
lateinit var USER: UserModel

const val NODE_USERS = "users"
const val NODE_USERNAMES = "usernames"
const val NODE_POSTS = "posts"
const val NODE_POSTS_USERS = "posts_users"

const val FOLDER_PROFILE_IMAGE = "profile_image"
const val FOLDER_POST_IMAGE = "post_image"

const val CHILD_ID = "id"
const val CHILD_AUTHOR = "author"
const val CHILD_PHONE = "phone"
const val CHILD_USERNAME = "username"
const val CHILD_FULL_NAME = "full_name"
const val CHILD_BIO = "bio"
const val CHILD_PHOTO_URL = "photo_url"
const val CHILD_STATE = "state"
const val CHILD_TITLE = "title"
const val CHILD_DESCRIPTION = "description"
const val CHILD_ADDRESS = "address"
const val CHILD_TIME_STAMP = "time_stamp"

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    REF_STORAGE_ROOT = FirebaseStorage.getInstance().reference
    AUTH.setLanguageCode("ru")
    USER = UserModel()
    CURRENT_UID = AUTH.currentUser?.uid.toString()
}

inline fun putUrlToDatabase(url: String, crossinline function: () -> Unit) {
    REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID)
        .child(CHILD_PHOTO_URL).setValue(url).addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}

inline fun putUrlToPost(url: String, postKey: String, crossinline function: () -> Unit) {
    REF_DATABASE_ROOT.child(NODE_POSTS).child(postKey)
        .child(CHILD_PHOTO_URL).setValue(url).addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}

inline fun getUrlFromStorage(path: StorageReference, crossinline function: (url: String) -> Unit) {
    path.downloadUrl.addOnSuccessListener { function(it.toString()) }
        .addOnFailureListener { showToast(it.message.toString()) }
}

inline fun putImageToStorage(uri: Uri, path: StorageReference, crossinline function: () -> Unit) {
    path.putFile(uri).addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}

inline fun initUser(crossinline function: () -> Unit) {
    REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID)
        .addListenerForSingleValueEvent(AppValueEventListener {
            USER = it.getValue(UserModel::class.java) ?: UserModel()
            if (USER.username.isEmpty()) {
                USER.username = CURRENT_UID
            }
            function()
        })
}

fun addNewPost(title: String, description: String, postKey: String, function: () -> Unit) {
    val mapPost = hashMapOf<String, Any>()

    mapPost[CHILD_ID] = postKey
    mapPost[CHILD_AUTHOR] = CURRENT_UID
    mapPost[CHILD_TITLE] = title
    mapPost[CHILD_DESCRIPTION] = description
    mapPost[CHILD_TIME_STAMP] = ServerValue.TIMESTAMP

    REF_DATABASE_ROOT.child(NODE_POSTS).child(postKey).updateChildren(mapPost)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }

    val mapUserPost = hashMapOf<String, Any>()

    mapUserPost[CHILD_ID] = postKey

    REF_DATABASE_ROOT.child(NODE_POSTS_USERS).child(CURRENT_UID).child(postKey).updateChildren(mapUserPost)
}