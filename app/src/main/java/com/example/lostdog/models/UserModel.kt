package com.example.lostdog.models

data class UserModel(
    val id: String = "",
    var username: String = "",
    var bio: String = "",
    var full_name: String = "",
    var phone: String = "",
    var photo_url: String = "empty",
    var state: String = ""
)