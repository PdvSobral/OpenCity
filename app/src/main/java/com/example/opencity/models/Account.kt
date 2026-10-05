package com.example.opencity.models


data class Account(
    val id: Int,
    val username: String,
    val displayName: String? = null,
    /*
    ID of the profile picture ON THE SERVER.
    The app will request by ID and save to a hardcoded place no local storage/cache.
    Probably later it can be removed, only needed on login, and it can be like a login packet
    */
    val profilePictureId: String? = null,
    val role: AccountRole = AccountRole.USER,
    // Only for simple UI checks. All rigorous enforcing will be server side
    val points: Int = 0,
    val createdAt: String? = null,
)

enum class AccountRole {
    USER,
    ADMINISTRATOR
}
