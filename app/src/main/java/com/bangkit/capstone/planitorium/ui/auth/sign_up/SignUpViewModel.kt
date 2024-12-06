package com.bangkit.capstone.planitorium.ui.auth.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone.planitorium.core.data.Result
import com.bangkit.capstone.planitorium.core.data.remote.response.auth.SignUpResponse
import com.bangkit.capstone.planitorium.core.data.repository.UserRepository
import kotlinx.coroutines.launch

class SignUpViewModel(private val repository: UserRepository) : ViewModel() {
    private val _signUpResult = MutableLiveData<Result<SignUpResponse>>()
    val signUpResult: LiveData<Result<SignUpResponse>> = _signUpResult

    fun signUp(email: String, username: String, password: String) {
        _signUpResult.value = Result.Loading
        viewModelScope.launch {
            repository.register(email, username, password).observeForever { result ->
                _signUpResult.value = result
            }
        }
    }
}