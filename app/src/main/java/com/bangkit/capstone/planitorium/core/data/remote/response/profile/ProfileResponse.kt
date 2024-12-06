package com.bangkit.capstone.planitorium.core.data.remote.response.profile

data class ProfileResponse(
	val profile: Profile? = null,
	val message: String? = null,
	val error: String? = null
)

data class Profile(
	val photo: String? = null,
	val email: String? = null,
	val username: String? = null
)

