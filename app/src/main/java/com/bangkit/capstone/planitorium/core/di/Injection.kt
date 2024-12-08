package com.bangkit.capstone.planitorium.core.di

import android.content.Context
import com.bangkit.capstone.planitorium.core.data.local.Preference
import com.bangkit.capstone.planitorium.core.data.local.dataStore
import com.bangkit.capstone.planitorium.core.data.remote.retrofit.ApiConfig
import com.bangkit.capstone.planitorium.core.data.repository.PlantRepository
import com.bangkit.capstone.planitorium.core.data.repository.UserRepository

object Injection {
    fun providePlantRepository(context: Context): PlantRepository {
        val pref = Preference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(pref)

        return PlantRepository.getInstance(apiService)
    }

    fun provideUserRepository(context: Context): UserRepository {
        val pref = Preference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(pref)

        return UserRepository.getInstance(apiService, pref)
    }
}