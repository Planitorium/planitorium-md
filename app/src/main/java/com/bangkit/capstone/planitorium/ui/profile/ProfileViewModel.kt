package com.bangkit.capstone.planitorium.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone.planitorium.core.data.remote.response.profile.ProfileResponse
import com.bangkit.capstone.planitorium.core.data.repository.UserRepository
import kotlinx.coroutines.launch
import com.bangkit.capstone.planitorium.core.data.Result
import java.io.File

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    private val _profileResult = MutableLiveData<Result<ProfileResponse>>()
    val profileResult: LiveData<Result<ProfileResponse>> = _profileResult

    fun getProfile() {
        _profileResult.value = Result.Loading
        viewModelScope.launch {
            repository.getProfile().observeForever { result ->
                _profileResult.value = result
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun uploadPhoto(photo: File) = repository.uploadPhoto(photo)
}