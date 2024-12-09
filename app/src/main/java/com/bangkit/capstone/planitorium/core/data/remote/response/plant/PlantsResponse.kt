package com.bangkit.capstone.planitorium.core.data.remote.response.plant

data class AddPlantResponse(
	val plant: PlantsItem,
	val message: String? = null
)

data class PlantListResponse(
	val plants: List<PlantsItem>,
	val message: String? = null,
	val error: Boolean? = null
)

data class PlantDetailResponse(
	val plant: PlantsItem,
	val error: Boolean? = null,
	val message: String? = null
)

data class PlantsItem(
	val createdAt: String? = null,
	val name: String? = null,
	val description: String? = null,
	val photo: String? = null,
	val startTime: String? = null,
	val id: String? = null,
	val endTime: String? = null,
	val updatedAt: String? = null
)

