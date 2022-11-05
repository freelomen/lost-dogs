package com.example.lostdog.models

data class PostModel (
    val id: String = "",
    var title: String = "",
    var description: String = "",
    var photo_url: String = "empty",
    var address: String = "",
    val state: String = "",
    val author: String = ""
)