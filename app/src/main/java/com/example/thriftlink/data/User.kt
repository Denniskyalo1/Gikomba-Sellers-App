package com.example.thriftlink.data

// The roles a user can have
enum class UserRole {
    BUYER,
    SELLER
}

// The core User data structure
data class User(
    val id: String,
    val email: String,
    val role: UserRole
)
