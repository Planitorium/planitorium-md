package com.bangkit.capstone.planitorium.ui.plant_list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.capstone.planitorium.data.repository.PlantRepository
import com.bangkit.capstone.planitorium.di.Injection

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