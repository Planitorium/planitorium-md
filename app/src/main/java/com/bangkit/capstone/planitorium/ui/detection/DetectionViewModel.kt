package com.bangkit.capstone.planitorium.ui.detection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone.planitorium.core.data.Result
import com.bangkit.capstone.planitorium.core.data.remote.response.detection.DetectionResponse
import com.bangkit.capstone.planitorium.core.data.repository.DetectionRepository
import kotlinx.coroutines.launch
import java.io.File

class DetectionViewModel(
    private val repository: DetectionRepository
) : ViewModel() {
    private val _detectionListResult = MutableLiveData<Result<DetectionResponse>>()
    val detectionListResult: LiveData<Result<DetectionResponse>> = _detectionListResult

    fun addDetection(photo: File?, name: String) = repository.addDetection(photo, name)

    fun getDetectionList() {
        _detectionListResult.value = Result.Loading
        viewModelScope.launch {
            repository.getDetectionList().observeForever { result ->
                _detectionListResult.value = result
            }
        }
    }
}