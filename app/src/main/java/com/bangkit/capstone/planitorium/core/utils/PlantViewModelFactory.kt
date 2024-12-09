package com.bangkit.capstone.planitorium.core.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.capstone.planitorium.core.data.repository.PlantRepository
import com.bangkit.capstone.planitorium.core.di.Injection
import com.bangkit.capstone.planitorium.ui.plant_list.PlantListViewModel

class PlantViewModelFactory (
    private val plantRepository: PlantRepository,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantListViewModel::class.java)) {
            return PlantListViewModel(plantRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {
        @Volatile
        private var instance: PlantViewModelFactory? = null
        fun getInstance(context: Context): PlantViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: PlantViewModelFactory(Injection.providePlantRepository(context))
            }.also { instance = it }
    }
}