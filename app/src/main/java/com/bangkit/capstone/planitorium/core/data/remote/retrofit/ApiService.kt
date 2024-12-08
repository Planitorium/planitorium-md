package com.bangkit.capstone.planitorium.core.data.remote.retrofit

import com.bangkit.capstone.planitorium.core.data.remote.request.SignInRequest
import com.bangkit.capstone.planitorium.core.data.remote.request.SignUpRequest
import com.bangkit.capstone.planitorium.core.data.remote.response.plant.AddPlantResponse
import com.bangkit.capstone.planitorium.core.data.remote.response.plant.PlantListResponse
import com.bangkit.capstone.planitorium.core.data.remote.response.auth.SignInResponse
import com.bangkit.capstone.planitorium.core.data.remote.response.auth.SignUpResponse
import com.bangkit.capstone.planitorium.core.data.remote.response.plant.PlantDetailResponse
import com.bangkit.capstone.planitorium.core.data.remote.response.profile.ProfileResponse
import com.bangkit.capstone.planitorium.core.data.remote.response.profile.UploadPhotoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    //// Authentication endpoint ////

    // Register
    @POST("api/auth/register")
    suspend fun registerApi(
        @Body request: SignUpRequest
    ): SignUpResponse

    // Login
    @POST("api/auth/login")
    suspend fun loginApi(
        @Body request: SignInRequest
    ): SignInResponse

    //// Authentication endpoint ////

    //// Profile endpoints ////
    @GET("api/profile")
    suspend fun getProfileApi(): ProfileResponse

    @Multipart
    @POST("api/profile/upload")
    suspend fun uploadPhotoApi(
        @Part photo: MultipartBody.Part
    ): UploadPhotoResponse

    //// Profile endpoints ////

    // Plant endpoints
    @Multipart
    @POST("api/plant/add")
    suspend fun addPlantApi(
        @Part photo: MultipartBody.Part?,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("startTime") startTime: RequestBody,
        @Part("endTime") endTime: RequestBody
    ): AddPlantResponse

    @GET("api/plant/list")
    suspend fun getPlantListApi(): PlantListResponse

    @GET("api/plant/detail/{id}")
    suspend fun getPlantDetailApi(
        @Path("id") id: String
    ): PlantDetailResponse

    @GET("api/plant/list/filter")
    suspend fun getPlantListByDateApi(
        @Query("endTime") endTime: String
    ): PlantListResponse


    //Plant Disease Detection
    @Multipart
    @POST("api/detection/add")
    fun addDetectionApi(
        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part,
        @Part ("name") name: String,
        @Part ("description") description: String
    ): Call<ResponseBody>

    @GET("api/detection/list")
    fun getDetectionListApi(): Call<ResponseBody>

    @GET("api/detection/photo/{filename}")
    fun getDetectionPhotoApi(
        @Path("filename") filename: String
    ): Call<ResponseBody>

}
