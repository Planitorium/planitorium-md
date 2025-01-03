package com.bangkit.capstone.planitorium.core.data.repository

import androidx.lifecycle.liveData
import com.bangkit.capstone.planitorium.core.data.local.Preference
import com.bangkit.capstone.planitorium.core.data.remote.retrofit.ApiService
import com.google.gson.Gson
import com.bangkit.capstone.planitorium.core.data.Result
import com.bangkit.capstone.planitorium.core.data.model.UserModel
import com.bangkit.capstone.planitorium.core.data.remote.request.SignInRequest
import com.bangkit.capstone.planitorium.core.data.remote.request.SignUpRequest
import com.bangkit.capstone.planitorium.core.data.remote.response.auth.SignInResponse
import com.bangkit.capstone.planitorium.core.data.remote.response.auth.SignUpResponse
import com.bangkit.capstone.planitorium.core.data.remote.response.profile.ProfileResponse
import com.bangkit.capstone.planitorium.core.data.remote.response.profile.UploadPhotoResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class UserRepository(private val apiService: ApiService, private val pref: Preference) {

    // Register
    fun register(email: String, username: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val request = SignUpRequest(email, username, password)
            val response = apiService.registerApi(request)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, SignUpResponse::class.java)
            emit(Result.Error(errorResponse.message.toString()))
        }
    }

    // Login
    fun login(email: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val request = SignInRequest(email, password)
            val response = apiService.loginApi(request)
            response.result?.let { data ->
                pref.saveSession(UserModel(data.userId!!, data.token!!))
            }
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, SignInResponse::class.java)
            emit(Result.Error(errorResponse.message.toString()))
        }
    }

    // Profile
    fun getProfile() = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getProfileApi()
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ProfileResponse::class.java)
            emit(Result.Error(errorResponse.error.toString()))
        }
    }

    // Upload Photo
    fun uploadPhoto(photo: File) = liveData {
        emit(Result.Loading)

        val requestPhoto = photo.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData("photo", photo.name, requestPhoto)

        try {
            val successResponse = apiService.uploadPhotoApi(multipartBody)
            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, UploadPhotoResponse::class.java)
            emit(Result.Error(errorResponse.message.toString()))
        }
    }

    // Get Session
    fun getSession(): Flow<UserModel> {
        return pref.getSession()
    }

    // Logout
    suspend fun logout() {
        pref.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(apiService: ApiService, pref: Preference) =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, pref)
            }.also { instance = it }
    }
}