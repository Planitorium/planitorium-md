package com.bangkit.capstone.planitorium.ui.plant_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.capstone.planitorium.core.data.remote.response.plant.PlantsItem
import com.bangkit.capstone.planitorium.core.data.repository.PlantRepository
import java.io.File

class PlantListViewModel (
    private val plantListRepository: PlantRepository
) : ViewModel() {
    val plantList: LiveData<List<PlantsItem>> = plantListRepository.getPlantList()

    fun addPlant(photo: File, plantName: String, description: String, startDate: String, endDate: String) {
        plantListRepository.addPlant("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFmaWZoYW16YWgxMkBnbWFpbC5jb20iLCJpYXQiOjE3MzM0MTU1ODYsImV4cCI6MTczMzQxOTE4Nn0.qUzmQe8oCieDf9FoSs_NwLVRL5_JfuPBy9cFe5f0T9E", photo, plantName, description, startDate, endDate)
    }

}

