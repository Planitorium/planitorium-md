package com.bangkit.capstone.planitorium.data.remote.retrofit

import com.bangkit.capstone.planitorium.data.remote.response.AddPlantResponse
import com.bangkit.capstone.planitorium.data.remote.response.GetPlantDetailResponse
import com.bangkit.capstone.planitorium.data.remote.response.PlantListResponse
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

interface ApiService {
    // Authentication endpoints
    @FormUrlEncoded
    @POST("api/auth/register")
    fun registerApi(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseBody>;

    @FormUrlEncoded
    @POST("api/auth/login")
    fun loginApi(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseBody>;

    @POST("api/auth/logout")
    fun logoutApi(
        @Query("token") active: String
    ): Call<ResponseBody>


    // Profile endpoints
    @GET("api/profile")
    fun getProfileApi(
        @Header("Authorization") token: String
    ): Call<ResponseBody>

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
