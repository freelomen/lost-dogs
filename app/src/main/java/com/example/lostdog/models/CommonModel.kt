package com.example.lostdog.models

data class CommonModel (
    val id: String = "",
    var username: String = "",
    var bio: String = "",
    var full_name: String = "",
    var phone: String = "",
    var photo_url: String = "empty",
    var state: String = "",
    var title: String = "",
    var description: String = "",
    var address: String = "",
    var time_stamp: String = ""
)