package com.bangkit.capstone.planitorium.core.data.remote.retrofit

import com.bangkit.capstone.planitorium.core.data.remote.request.SignInRequest
import com.bangkit.capstone.planitorium.core.data.remote.request.SignUpRequest
import com.bangkit.capstone.planitorium.core.data.remote.response.plant.AddPlantResponse
import com.bangkit.capstone.planitorium.core.data.remote.response.plant.GetPlantDetailResponse
import com.bangkit.capstone.planitorium.core.data.remote.response.plant.PlantListResponse
import com.bangkit.capstone.planitorium.core.data.remote.response.auth.SignInResponse
import com.bangkit.capstone.planitorium.core.data.remote.response.auth.SignUpResponse
import com.bangkit.capstone.planitorium.core.data.remote.response.profile.ProfileResponse
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

    @POST("api/auth/logout")
    fun logoutApi(
        @Query("token") active: String
    ): Call<ResponseBody> // Belum kuubah gegara, API masih salah

    //// Authentication endpoint ////

    //// Profile endpoints ////
    @GET("api/profile")
    suspend fun getProfileApi(): ProfileResponse

    @GET("api/profile/photo/{filename}")
    fun getPhotoApi(
        @Header("Authorization") token: String,
        @Path("filename") filename : String
    ): Call<ResponseBody>

    @Multipart
    @POST("api/profile/upload")
    fun uploadPhotoApi(
        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part
    ): Call<ResponseBody>

    //// Profile endpoints ////

    // Plant endpoints
    @Multipart
    @POST("api/plant/add")
    fun addPlantApi(
        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part,
        @Part ("plantName") planeName: RequestBody,
        @Part("description") description: RequestBody,
        @Part("startDate") startDate: RequestBody,
        @Part("endDate") endDate: RequestBody
    ): Call<AddPlantResponse>

    @GET("api/plant/list")
    fun getPlantListApi(): Call<PlantListResponse>

    @GET("api/plant/detail/{id}")
    fun getPlantDetailApi(
        @Path("id") id: String
    ): Call<GetPlantDetailResponse>


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