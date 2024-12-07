package com.bangkit.capstone.planitorium.ui.plant_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone.planitorium.core.data.repository.PlantRepository
import java.io.File
import com.bangkit.capstone.planitorium.core.data.Result
import com.bangkit.capstone.planitorium.core.data.remote.response.plant.AddPlantResponse
import kotlinx.coroutines.launch

class PlantListViewModel (
    private val repository: PlantRepository
) : ViewModel() {
    private val _plantAddStatus = MutableLiveData<Result<AddPlantResponse>>()
    val plantAddStatus: LiveData<Result<AddPlantResponse>> = _plantAddStatus

    fun addPlant(photo: File?, name: String, description: String, startTime: String, endTime: String) {
        _plantAddStatus.value = Result.Loading
        viewModelScope.launch {
            repository.addPlant(photo, name, description, startTime, endTime).observeForever { result ->
                _plantAddStatus.value = result
            }
        }
    }

    fun getPlantList() = repository.getPlantList()

    fun getPlantDetail(id: String) = repository.getPlantDetail(id)

    fun getPlantListByDate(date: String) = repository.getPlantListByDate(date)

}

