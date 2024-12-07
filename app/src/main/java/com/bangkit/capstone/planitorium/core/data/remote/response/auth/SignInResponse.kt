package com.bangkit.capstone.planitorium.core.data.remote.response.auth

data class SignInResponse(
	val result: Result? = null,
	val error: Boolean? = null,
	val message: String? = null
)

data class Result(
	val userId: String? = null,
	val token: String? = null
)

// Sementara
//data class SignInResponse (
//	val token: String? = null,
//	val message: String? = null,
//)


