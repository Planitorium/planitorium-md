package com.bangkit.capstone.planitorium.core.data.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.bangkit.capstone.planitorium.core.data.Result
import com.bangkit.capstone.planitorium.core.data.remote.response.plant.AddPlantResponse
import com.bangkit.capstone.planitorium.core.data.remote.response.plant.PlantDetailResponse
import com.bangkit.capstone.planitorium.core.data.remote.response.plant.PlantListResponse
import com.bangkit.capstone.planitorium.core.data.remote.retrofit.ApiService
import com.bangkit.capstone.planitorium.core.utils.AppExecutors
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class PlantRepository (private val apiService: ApiService, private val executors: AppExecutors){

    fun addPlant(photo: File?, name: String, description: String, startTime: String, endTime: String) = liveData {
        emit(Result.Loading)
        try {
            val photoPart = photo?.let {
                MultipartBody.Part.createFormData(
                    "photo",
                    it.name,
                    it.asRequestBody("image/*".toMediaTypeOrNull())
                )
            }

            val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
            val descriptionBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
            val startTimeBody = startTime.toRequestBody("text/plain".toMediaTypeOrNull())
            val endTimeBody = endTime.toRequestBody("text/plain".toMediaTypeOrNull())

            val response = apiService.addPlantApi(photoPart, nameBody, descriptionBody, startTimeBody, endTimeBody)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, AddPlantResponse::class.java)
            emit(Result.Error(errorResponse.message.toString()))
        } catch (e: Exception) {
            emit(Result.Error("An unexpected error occurred: ${e.localizedMessage}"))
        }
    }


    fun getPlantList() = liveData {
         emit(Result.Loading)
        try{
            val response = apiService.getPlantListApi()
            emit(Result.Success(response))
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, PlantListResponse::class.java)
            emit(Result.Error(errorResponse.error.toString()))
        }
    }

     fun getPlantDetail(id: String) = liveData {
         emit(Result.Loading)
       try{
           val response = apiService.getPlantDetailApi(id)
           emit(Result.Success(response))
       }catch (e: HttpException){
           val errorBody = e.response()?.errorBody()?.string()
           val errorResponse = Gson().fromJson(errorBody, PlantDetailResponse::class.java)
           emit(Result.Error(errorResponse.error.toString()))
       }
    }

    fun getPlantListByDate(date: String) = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getPlantListByDateApi(date)
            val plants = response.plants ?: emptyList()
            emit(Result.Success(response.copy(plants = plants)))
        } catch (e: HttpException) {
            if (e.code() == 404) {
                // Return an empty list if the API returns 404
                emit(Result.Success(PlantListResponse(emptyList())))
            } else {
                // Handle other HTTP errors
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, PlantListResponse::class.java)
                emit(Result.Error(errorResponse.error.toString()))
            }
        } catch (e: Exception) {
            // Handle non-HTTP exceptions (e.g., network issues)
            emit(Result.Error(e.localizedMessage ?: "Unknown error occurred"))
        }
    }

    companion object {
        @Volatile
        private var instance: PlantRepository? = null

        fun getInstance(apiService: ApiService, executors: AppExecutors): PlantRepository {
            return instance ?: synchronized(this) {
                instance ?: PlantRepository(apiService, executors).also { instance = it }
            }
        }
    }
}