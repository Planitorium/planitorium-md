package com.bangkit.capstone.planitorium.core.data.remote.response.detection

data class DetectionDetailResponse(
	val detection: Detection? = null,
	val error: Boolean? = null
)

data class Detection(
	val result: String? = null,
	val photoUrl: String? = null,
	val createdAt: String? = null,
	val confidence: String? = null,
	val suggestion: String? = null,
	val photo: String? = null,
	val id: String? = null,
	val plantName: String? = null
)

