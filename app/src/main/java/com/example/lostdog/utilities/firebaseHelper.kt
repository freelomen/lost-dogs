package com.example.lostdog.utilities

import com.example.lostdog.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

lateinit var AUTH: FirebaseAuth
lateinit var UID: String
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var USER: User

const val NODE_USERS = "users"
const val NODE_USERNAMES = "usernames"

const val CHILD_ID = "id"
const val CHILD_PHONE = "phone"
const val CHILD_USERNAME = "username"
const val CHILD_FULL_NAME = "full_name"
const val CHILD_BIO = "bio"
const val CHILD_PHOTO_URL = "photo_url"

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    AUTH.setLanguageCode("ru")
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    USER = User()
    UID = AUTH.currentUser?.uid.toString()
}