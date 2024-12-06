package com.bangkit.capstone.planitorium.core.data.remote.response.plant

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PlantListResponse(

	@field:SerializedName("plants")
	val plants: List<PlantsItem?>? = null
) : Parcelable

@Parcelize
data class UpdatedAt(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int? = null,

	@field:SerializedName("_seconds")
	val seconds: Int? = null
) : Parcelable

@Parcelize
data class StartTime(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int? = null,

	@field:SerializedName("_seconds")
	val seconds: Int? = null
) : Parcelable

@Parcelize
data class EndTime(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int? = null,

	@field:SerializedName("_seconds")
	val seconds: Int? = null
) : Parcelable

@Parcelize
data class PlantsItem(

	@field:SerializedName("createdAt")
	val createdAt: CreatedAt? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("startTime")
	val startTime: StartTime? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("endTime")
	val endTime: EndTime? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: UpdatedAt? = null
) : Parcelable

@Parcelize
data class CreatedAt(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int? = null,

	@field:SerializedName("_seconds")
	val seconds: Int? = null
) : Parcelable


@Parcelize
data class AddPlantResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null

) : Parcelable


@Parcelize
data class GetPlantDetailResponse(
	@field:SerializedName("plantDetail")
	val plantDetail: PlantItem,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null

): Parcelable

@Parcelize
data class PlantItem(
	@field:SerializedName("photo")
	val photo: String,

	@field:SerializedName("plantName")
	val plantName: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("startDate")
	val startDate: String,

	@field:SerializedName("harvestDate")
	val endDate: String

): Parcelable

