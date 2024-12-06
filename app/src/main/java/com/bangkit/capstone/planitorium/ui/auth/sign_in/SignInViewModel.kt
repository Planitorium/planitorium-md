package com.bangkit.capstone.planitorium.ui.auth.sign_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone.planitorium.core.data.Result
import com.bangkit.capstone.planitorium.core.data.remote.response.auth.SignInResponse
import com.bangkit.capstone.planitorium.core.data.repository.UserRepository
import kotlinx.coroutines.launch

class SignInViewModel(private val repository: UserRepository) : ViewModel() {
    private val _signInResult = MutableLiveData<Result<SignInResponse>>()
    val signInResult: LiveData<Result<SignInResponse>> = _signInResult

    fun signIn(email: String, password: String) {
        _signInResult.value = Result.Loading
        viewModelScope.launch {
            repository.login(email, password).observeForever { result ->
                _signInResult.value = result
            }
        }
    }
}