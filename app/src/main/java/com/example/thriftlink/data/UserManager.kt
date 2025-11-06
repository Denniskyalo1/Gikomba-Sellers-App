package com.example.thriftlink.data

object UserManager {
    enum class UserType { BUYER, SELLER }
    var currentUserType: UserType? = null
    var currentUserEmail: String? = null

    fun login(userType: UserType, email: String) {
        currentUserType = userType
        currentUserEmail = email
    }

    fun logout() {
        currentUserType = null
        currentUserEmail = null

    }
    fun register(userType: UserType, email: String) {
        // In a real app, this is where you'd handle API calls, database writes, etc.
        // For now, we'll log them in immediately after "registering"
        login(userType, email)
    }
}
