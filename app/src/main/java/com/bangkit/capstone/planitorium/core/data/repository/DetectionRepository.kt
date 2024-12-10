package com.bangkit.capstone.planitorium.core.data.repository

import androidx.lifecycle.liveData
import com.bangkit.capstone.planitorium.core.data.Result
import com.bangkit.capstone.planitorium.core.data.remote.response.detection.DetectionDetailResponse
import com.bangkit.capstone.planitorium.core.data.remote.response.detection.DetectionResponse
import com.bangkit.capstone.planitorium.core.data.remote.response.plant.AddPlantResponse
import com.bangkit.capstone.planitorium.core.data.remote.retrofit.ApiService
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class DetectionRepository (private val apiService: ApiService){

    fun addDetection(photo: File?, name: String) = liveData {
        emit(Result.Loading)
        try {

            val photoPart = photo?.let {
                val mimeType = when {
                    it.name.endsWith(".png", ignoreCase = true) -> "image/png"
                    it.name.endsWith(".jpg", ignoreCase = true) || it.name.endsWith(".jpeg", ignoreCase = true) -> "image/jpeg"
                    else -> "image/*" // Fallback MIME type
                }
                MultipartBody.Part.createFormData(
                    "photo",
                    it.name,
                    it.asRequestBody(mimeType.toMediaTypeOrNull())
                )
            }
            val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
            val response = apiService.addDetectionApi(photoPart, nameBody)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, AddPlantResponse::class.java)
            emit(Result.Error(errorResponse.message.toString()))
        } catch (e: Exception) {
            emit(Result.Error("An unexpected error occurred: ${e.localizedMessage}"))
        }
    }

    fun getDetectionList() = liveData {
        emit(Result.Loading)
        try{
            val response = apiService.getDetectionListApi()
            emit(Result.Success(response))
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, DetectionResponse::class.java)
            emit(Result.Error(errorResponse.error.toString()))
        }
    }

    fun getDetectionDetail(id: String) = liveData {
        emit(Result.Loading)
        try{
            val response = apiService.getDetectionDetail(id)
            emit(Result.Success(response))
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, DetectionDetailResponse::class.java)
            emit(Result.Error(errorResponse.error.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: DetectionRepository? = null

        fun getInstance(apiService: ApiService): DetectionRepository {
            return instance ?: synchronized(this) {
                instance ?: DetectionRepository(apiService).also { instance = it }
            }
        }
    }
}