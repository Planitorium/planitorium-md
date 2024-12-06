package com.bangkit.capstone.planitorium.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.capstone.planitorium.data.remote.response.AddPlantResponse
import com.bangkit.capstone.planitorium.data.remote.response.GetPlantDetailResponse
import com.bangkit.capstone.planitorium.data.remote.response.PlantItem
import com.bangkit.capstone.planitorium.data.remote.response.PlantListResponse
import com.bangkit.capstone.planitorium.data.remote.response.PlantsItem
import com.bangkit.capstone.planitorium.data.remote.retrofit.ApiService
import com.bangkit.capstone.planitorium.utils.AppExecutors
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PlantRepository (private val apiService: ApiService, private val executors: AppExecutors){

    private val _plantList = MutableLiveData<List<PlantsItem>>()
//    val plantList: LiveData<List<PlantsItem>> get() = _plantList
    private var plantDetail: PlantItem? = null

     fun addPlant(token: String, imageFile: File, plantName: String, description: String, startDate: String, endDate: String){
        val plantNameBody = plantName.toRequestBody("text/plain".toMediaTypeOrNull())
        val descriptionBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
        val startDateBody = startDate.toRequestBody("text/plain".toMediaTypeOrNull())
        val endDateBody = endDate.toRequestBody("text/plain".toMediaTypeOrNull())
        val imageRequestBody = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("photo", imageFile.name, imageRequestBody)

        val client = apiService.addPlantApi(token, imagePart, plantNameBody, descriptionBody, startDateBody, endDateBody)

        client.enqueue(object: Callback<AddPlantResponse> {
            override fun onResponse(
                call: Call<AddPlantResponse>,
                response: Response<AddPlantResponse>
            ) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody?.error == false) {
                        Log.d("Add Plant Successful", "Message: ${responseBody.message}")
                    } else {
                        Log.e("Add Plant Failed", "Error message: ${responseBody?.message}")
                    }
                }else{
                    Log.e("Add Plant Failed", "Response not successful: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<AddPlantResponse>, t: Throwable) {
                Log.e("Add Plant Failed", t.toString())
            }
        })
    }

     fun getPlantList(): LiveData<List<PlantsItem>> {
        val client = apiService.getPlantListApi()
        client.enqueue(object: Callback<PlantListResponse> {
            override fun onResponse(
                call: Call<PlantListResponse>,
                response: Response<PlantListResponse>
            ) {
                if (response.isSuccessful){
                    val responseBody = response.body()?.plants
                    Log.d("Response", responseBody?.size.toString())
                    executors.diskIO.execute{
                        responseBody?.let {
                            _plantList.postValue(it.map { item ->
                                PlantsItem(
                                    item?.createdAt,
                                    item?.name,
                                    item?.description,
                                    item?.photo,
                                    item?.startTime,
                                    item?.id,
                                    item?.endTime,
                                    item?.updatedAt
                                )
                            })
                        }
                    }
                }
            }

            override fun onFailure(call: Call<PlantListResponse>, t: Throwable) {
                Log.e("Get Plant List Failed", t.toString())
            }
        })
        return _plantList
    }

     fun getPlantDetail(id: String): PlantItem? {
        val client = apiService.getPlantDetailApi(id)
        client.enqueue(object: Callback<GetPlantDetailResponse> {
            override fun onResponse(
                call: Call<GetPlantDetailResponse>,
                response: Response<GetPlantDetailResponse>
            ) {
                if (response.isSuccessful){
                    val responseBody = response.body()?.plantDetail
                    executors.diskIO.execute{
                        if (responseBody != null) {
                            plantDetail = PlantItem(
                                responseBody.photo,
                                responseBody.plantName,
                                responseBody.description,
                                responseBody.startDate,
                                responseBody.endDate
                            )
                        }
                    }
                }
            }

            override fun onFailure(call: Call<GetPlantDetailResponse>, t: Throwable) {
                Log.e("Get Plant Detail Failed", t.toString())
            }
        })
        return plantDetail
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