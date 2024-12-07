package com.bangkit.capstone.planitorium.core.data.remote.response.auth

data class SignInResponse(
	val error: Boolean? = null,
	val message: String? = null,
	val result: Result? = null
)

data class Result(
	val userId: String? = null,
	val username: String? = null,
	val token: String? = null
)
