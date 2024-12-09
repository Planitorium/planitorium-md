package com.bangkit.capstone.planitorium.core.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.capstone.planitorium.core.data.repository.DetectionRepository
import com.bangkit.capstone.planitorium.core.di.Injection
import com.bangkit.capstone.planitorium.ui.detection.DetectionViewModel
import com.bangkit.capstone.planitorium.ui.plant_list.PlantListViewModel

class DetectionViewModelFactory(
        private val detectionRepository: DetectionRepository,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetectionViewModel::class.java)) {
            return DetectionViewModel(detectionRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {
        @Volatile
        private var instance: DetectionViewModelFactory? = null
        fun getInstance(context: Context): DetectionViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: DetectionViewModelFactory(Injection.provideDetectionRepository(context))
            }.also { instance = it }
    }
}