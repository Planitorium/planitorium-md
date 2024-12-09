package com.bangkit.capstone.planitorium.ui.detection

import androidx.lifecycle.ViewModel
import com.bangkit.capstone.planitorium.core.data.repository.DetectionRepository
import java.io.File

class DetectionViewModel(
    private val repository: DetectionRepository
) : ViewModel() {
    fun addDetection(photo: File?, name: String) = repository.addDetection(photo, name)
    fun getDetectionList() = repository.getDetectionList()
}