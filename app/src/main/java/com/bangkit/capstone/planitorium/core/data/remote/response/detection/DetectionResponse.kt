package com.bangkit.capstone.planitorium.core.data.remote.response.detection

data class DetectionResponse(
	val data: Data? = null,
	val error: Boolean? = null,
	val message: String? = null
)

data class AddDetectionResponse(
	val data: DetectionsItem? = null,
	val error: Boolean? = null,
	val message: String? = null
)

data class DetectionsItem(
	val result: String? = null,
	val createdAt: String? = null,
	val photoUrl: String? = null,
	val suggestion: String? = null,
	val confidence: String? = null,
	val plantName: String? = null
)

data class Data(
	val detections: List<DetectionsItem>? = null
)
