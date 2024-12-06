package com.bangkit.capstone.planitorium.core.data.model

data class UserModel(
    val userId: String,
    val token: String,
    val isLogin: Boolean = false
)