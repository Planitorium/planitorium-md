package com.bangkit.capstone.planitorium.di

import android.content.Context
import com.bangkit.capstone.planitorium.data.remote.retrofit.ApiConfig
import com.bangkit.capstone.planitorium.data.remote.retrofit.ApiService
import com.bangkit.capstone.planitorium.data.repository.PlantRepository
import com.bangkit.capstone.planitorium.utils.AppExecutors

object Injection {
    private var apiService: ApiService? = null
    private var plantRepository: PlantRepository? = null


    fun providePlantRepository(context: Context): PlantRepository {
        val apiService = apiService ?: ApiConfig.getApiService().also { apiService = it }
        val appExecutors = AppExecutors()

        return plantRepository ?: PlantRepository.getInstance(apiService,appExecutors).also {
            plantRepository = it
        }
    }
}