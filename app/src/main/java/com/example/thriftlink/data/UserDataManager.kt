package com.example.thriftlink.data

import android.content.Context

class UserDataManager(private val context: Context) {

    enum class UserType { BUYER, SELLER }

    /**
     * Handles login and returns a User object.
     * Replace with real backend / API call later.
     */
    fun login(userType: UserType, email: String, password: String): User {
        val role = when (userType) {
            UserType.BUYER -> UserRole.BUYER
            UserType.SELLER -> UserRole.SELLER
        }

        return User(
            id = email, // replace with backend-generated ID later
            email = email,
            role = role
        )
    }

    /**
     * Handles registration and returns the created User.
     */
    fun register(userType: UserType, email: String, password: String): User {
        val role = when (userType) {
            UserType.BUYER -> UserRole.BUYER
            UserType.SELLER -> UserRole.SELLER
        }

        return User(
            id = email, // replace with backend-generated ID later
            email = email,
            role = role
        )
    }
}
