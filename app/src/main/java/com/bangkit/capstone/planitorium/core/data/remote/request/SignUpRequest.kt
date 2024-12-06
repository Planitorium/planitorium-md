package com.bangkit.capstone.planitorium.core.data.remote.request

data class SignUpRequest(
    val email: String,
    val username: String,
    val password: String
)