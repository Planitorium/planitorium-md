package com.bangkit.capstone.planitorium.ui.disease_detection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiseaseDetectionViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Disease Detection Fragment"
    }
    val text: LiveData<String> = _text
}