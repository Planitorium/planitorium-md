package com.bangkit.capstone.planitorium.core.data.remote.response.plant

import com.google.gson.annotations.SerializedName

data class AddPlantResponse(
	val plant: PlantsItem,
	val message: String? = null
)

data class PlantListResponse(
	val plants: List<PlantsItem?>? = null,
	val message: String? = null,
	val error: String? = null
)

data class PlantDetailResponse(
	val plant: PlantsItem,
	val error: Boolean? = null,
	val message: String? = null
)

data class PlantsItem(
	val createdAt: CreatedAt? = null,
	val name: String? = null,
	val description: String? = null,
	val photo: String? = null,
	val startTime: String? = null,
	val id: String? = null,
	val endTime: String? = null,
	val updatedAt: UpdatedAt? = null
)

data class CreatedAt(
	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int? = null,

	@field:SerializedName("_seconds")
	val seconds: Int? = null
)

data class UpdatedAt(
	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int? = null,

	@field:SerializedName("_seconds")
	val seconds: Int? = null
)

data class Time(
	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int? = null,

	@field:SerializedName("_seconds")
	val seconds: Int? = null
)




