package com.example.opencity.models

data class Message(
    val author: String,
    val id: Int,
    val title: String,
    val body: String,
    val date: String,
    val isRead: Boolean = false
)
